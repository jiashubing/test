package common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import common.entity.Result;
import custom.BASE64Decoder;
import forum.util.FileEcodeUtil;
import forum.util.ImgUtil;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

/**
 * Created by jiashubing on 2015/9/12.
 * 
 * @author jiahsubing
 */
@Controller
public class FileUploadController {

	private static BASE64Decoder decoder = new BASE64Decoder();

	@RequestMapping(value = "/forum/fileUploadImage", method = RequestMethod.POST)
	@ResponseBody
	public Result fileUploadUeImage(HttpServletRequest request,
			HttpServletResponse response) {
		Result result = new Result();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		MultipartFile file = fileMap.get("imgFile");
		response.setHeader("X-frame-Options", "SAMEORIGIN");
		String returnUri = "";
		try (InputStream tmpStream = file.getInputStream();
				InputStream outStream = file.getInputStream()) {
			if (ImageIO.read(tmpStream) == null || file.getSize() == 0) {
				result.setError(1);
				result.setMessage("图片格式错误！");
				return result;
			}

			// 上传至服务器，先将图片保存到临时文件夹
			String realPath = request.getSession().getServletContext()
					.getRealPath(ImgUtil.TMPIMG_PATH);
			String imgName = UUID.randomUUID().toString();
			String imgPath = "/" + imgName + ".png";
			File myFile = new File(realPath + imgPath);
			FileEcodeUtil.inputStreamToFile(outStream, myFile);

			// 组装返回的URI，组装时是带有前缀的，但是在富文本框展示的时候，就只有URI了
			String servletPath = request.getServletPath();
			StringBuffer requestURL = request.getRequestURL();
			returnUri = requestURL.toString().substring(0,
					requestURL.length() - servletPath.length());
			returnUri += "/image/images/tmpImg" + imgPath;

		} catch (IOException e) {
			// e.printStackTrace();
		}

		result.setError(0);
		result.setUrl(returnUri);
		return result;
	}

	/**
	 * Created by jiashubing on 2015/9/9. ctrl+v富文本粘贴图片的ajax上传
	 * 
	 * @param imgsrc
	 *            图片网络地址
	 */
	@RequestMapping("/forum/ajaxEditorFileUpload")
	@ResponseBody
	public Result ajaxEditorFileUpload(String imgsrc, HttpServletRequest request) {
		Result result = new Result();
		// 去掉字符串前面多余的字符"data:image/png;base64,"，获得纯粹的二进制流
		imgsrc = imgsrc.substring(22);

		String returnUri = "";
		try (ByteArrayInputStream bais = new ByteArrayInputStream(
				decoder.decodeBuffer(imgsrc))) {
			// 将String转换成InputStream流

			// 上传至服务器，先将图片保存到临时文件夹
			String realPath = request.getSession().getServletContext()
					.getRealPath(ImgUtil.TMPIMG_PATH);
			String imgName = UUID.randomUUID().toString();
			String imgPath = "/" + imgName + ".png";
			File myFile = new File(realPath + imgPath);
			FileEcodeUtil.inputStreamToFile(bais, myFile);

			// 组装返回的URI，组装时是带有前缀的，但是在富文本框展示的时候，就只有URI了
			String servletPath = request.getServletPath();
			StringBuffer requestURL = request.getRequestURL();
			returnUri = requestURL.toString().substring(0,
					requestURL.length() - servletPath.length());
			returnUri += "/image/images/tmpImg" + imgPath;
			// System.out.println("returnUri = "+returnUri);

			result.setStatus(0);
			// result.setMessage(fileName);
			result.setUrl(returnUri);
		} catch (IOException e) {
			// e.printStackTrace();
		}
		return result;
	}

}
