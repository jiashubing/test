package common.service.Impl;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import common.service.JavaToPdfService;
import forum.po.ReplyContent;
import forum.po.Topic;
import forum.po.TopicContent;
import forum.service.ReplyService;
import forum.service.TopicService;
import forum.util.ImgUtil;

@Service("javaToPdfServiceImpl")
public class JavaToPdfServiceImpl implements JavaToPdfService {

	private static BaseFont bfChinese = null;
	private static Font chineseFont = null;

	@Value("#{configProperties['http.linux']}")
	private String linuxUrl;

	static {
		try {
			// bfChinese =
			// BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
			String filePath = Thread.currentThread().getContextClassLoader()
					.getResource("/").getPath();
			filePath += "resourcesImg/kaiti.ttf";
			bfChinese = BaseFont.createFont(filePath, BaseFont.IDENTITY_H,
					BaseFont.NOT_EMBEDDED);
			chineseFont = new Font(bfChinese);
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	@Resource(name = "topicServiceImpl")
	private TopicService topicService;
	
	@Resource(name = "replyServiceImpl")
	private ReplyService replyService;

	@Override
	public Map<String, Object> htmlToMap(long topicId) {

		Map<String, Object> map = new HashMap<String, Object>();

		Topic topic = topicService.findTopicById(topicId);
		map.put("title", topic.getTitle());
		map.put("sectionName", topic.getSection().getName());
		map.put("userName", topic.getUser().getNickName());
		map.put("publishTime", topic.getPublishTime());

		TopicContent topicContent = topicService
				.findTopicContentByTopicId(topicId);
		List<String> list = new ArrayList<String>();
		getPdfContent(topicContent.getContent(), list);
		
		List<ReplyContent> replyContentList = replyService.getReplyContentListByTopicId(topicId,100,0);
		for(int i=0; i<replyContentList.size(); i++){
			ReplyContent replyContent = replyContentList.get(i);
			list.add(" ");
			list.add(i+1+"楼");
			getPdfContent(replyContent.getContent(),list);
		}
		
		map.put("content", list);

		return map;
	}

	private void getPdfContent(String content, List<String> list) {
		String ans = shubingHtml2Txt(content);

		String[] s = ans.split("<br />");
		for (String value : s) {
			if (value == null) {
				list.add("");
			} else {
				value = value.trim();
				value = value.replaceAll("&nbsp;", " ");
				value = checkImage(value);
				list.add(value);
			}
		}
	}

	private String checkImage(String html) {
		String tmp = html.trim();
		if (tmp.startsWith("<img ") && tmp.contains("reaImg/") && tmp.contains(".png")) {
			return tmp;
		} else {
			html = html.replaceAll("\\<img[^>]*>|</img>(?i)", "");
			return html;
		}
	}

	private String shubingHtml2Txt(String html) {
		html = html
				.replaceAll(
						"\\<p[^>]*>|<span[^>]*>|</span>|<div[^>]*>|</div>|<b>|</b>(?i)",
						"");
		html = html.replaceAll("</p>", "<br />");// 去掉换行
		return html.trim();
	}

	public String mapToPdf(Map<String, Object> map) {

		UUID uuid = UUID.randomUUID();
		String outPath = ImgUtil.ABSOLUTE_TMPPDF_PATH + uuid + ".pdf";

		Document document = new Document();
		try (FileOutputStream fileOutputStream = new FileOutputStream(outPath)) {
			// 建立一个书写器
			PdfWriter writer = PdfWriter
					.getInstance(document, fileOutputStream);

			// 打开文件
			document.open();

			// 中文字体,解决中文不能显示问题
			if (bfChinese == null) {
				String filePath = Thread.currentThread()
						.getContextClassLoader().getResource("/").getPath();
				filePath += "resourcesImg/kaiti.ttf";
				bfChinese = BaseFont.createFont(filePath, BaseFont.IDENTITY_H,
						BaseFont.NOT_EMBEDDED);
			}
			if (chineseFont == null) {
				chineseFont = new Font(bfChinese);
			}

			// 添加内容
			if (map.get("title") != null) {
				String title = (String) map.get("title");
				Font blueFont = new Font(bfChinese, 25, Font.BOLD);
				blueFont.setColor(BaseColor.BLUE);
				Paragraph paragraph = new Paragraph(title, blueFont);
				paragraph.setLeading((float) 35);
				// 居中
				paragraph.setAlignment(1);
				document.add(paragraph);
			}

			// 蓝色字体
			Font blueFont = new Font(bfChinese);
			blueFont.setColor(BaseColor.ORANGE);
			// 红色字体
			Font redFont = new Font(bfChinese);
			redFont.setColor(BaseColor.BLACK);

			// 段落文本
			Paragraph parAuthour = new Paragraph();
			parAuthour.setAlignment(2);
			parAuthour.setLeading((float) 25);

			Chunk chunkBlue = null;
			Chunk chunkRed = null;
			if (map.get("sectionName") != null) {
				String sectionName = (String) map.get("sectionName");
				chunkBlue = new Chunk(sectionName, blueFont);
			}
			if (map.get("userName") != null) {
				String userName = (String) map.get("userName");
				chunkRed = new Chunk(" - " + userName, redFont);
			}
			parAuthour.add(chunkBlue);
			parAuthour.add(chunkRed);
			document.add(parAuthour);
			// }
			if (map.get("content") != null) {
				@SuppressWarnings("unchecked")
				List<String> contentList = (List<String>) map.get("content");
				for (String str : contentList) {
					addContentCustom(document, str);
				}
			}
			if (map.get("zxing") != null) {
				String filePath = ImgUtil.ABSOLUTE_TMPIMG_PATH + map.get("zxing");
				Image image = Image.getInstance(filePath);
				image.setAlignment(Image.MIDDLE);
				image.scaleAbsolute(200, 200);
				document.add(image);
				// 绿色字体
				Font greenFont = new Font(bfChinese);
				greenFont.setColor(BaseColor.GREEN);
				Paragraph parEnd = new Paragraph("扫描二维码可以跳转到对应的网页", greenFont);
				parEnd.setAlignment(1);
				document.add(parEnd);
			}

			// 图片1
			// Image image1 = Image.getInstance("E:/test.gif");
			// image1.setAlignment(Image.MIDDLE);
			// //设置图片位置的x轴和y周
			// // image1.setAbsolutePosition(100f, 550f);
			// //设置图片的宽度和高度
			// // image1.scaleAbsolute(200, 200);
			// //将图片1添加到pdf文件中
			// //绿色字体
			// Font greenFont = new Font(bfChinese);
			// // greenFont.setColor(BaseColor.GREEN);
			// // Paragraph chapterTitle = new Paragraph("段落标题xxxx",greenFont);
			// // chapterTitle.add(image1);
			// // document.add(chapterTitle);
			// document.add(image1);
			// document.add(image1);
			// document.add(image1);
			// document.add(image1);
			// document.add(image1);
			// document.add(new Paragraph("啦啦啦",greenFont));
			// document.add(image1);
			//
			// //图片2
			// Image image2 = Image.getInstance(new
			// URL("http://static.cnblogs.com/images/adminlogo.gif"));
			// //将图片2添加到pdf文件中
			// document.add(image2);

			// 关闭文档
			document.close();
			// 关闭书写器
			writer.close();
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {

		}
		return outPath;
	}

	private void addContentCustom(Document document, String str) {

		try {
			if (str.startsWith("<img ") && str.contains("reaImg/") && str.contains(".png")) {
				String real = str.substring(str.indexOf("reaImg/"),
						str.indexOf(".png"));
				real = real.substring(7);
				String filePath = ImgUtil.ABSOLUTE_REAL_PATH + real + ".png";

				Image image = Image.getInstance(filePath);
				image.setAlignment(Image.MIDDLE);
				// 设置图片位置的x轴和y轴
				// image1.setAbsolutePosition(100f, 550f);
				// 设置图片的宽度和高度
				// image1.scaleAbsolute(200, 200);
				document.add(image);
			} else {
				Font font = chineseFont;
				if (str.contains("<strong>")) {
					str = str.replaceAll("\\<strong>|</strong>(?i)", "");
					font = new Font(bfChinese, -1.0F, Font.BOLD);
				}
				if (str.startsWith("    ")) {
					str = "    " + str.trim();
				}
				document.add(new Paragraph(str, font));
			}
		} catch (Exception e) {
			// do nothing
		}

	}

}
