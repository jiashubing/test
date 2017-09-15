package common.service.Impl;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import common.service.ZXingService;

@Service("zxingServiceImpl")
public class ZXingServiceImpl implements ZXingService {
	private static final int QRCOLOR = 0xFF000000; // 默认是黑色
	private static final int BGWHITE = 0xFFFFFFFF; // 背景颜色

	/**
	 * 生成带logo的二维码图片
	 * 
	 * @param qrPic
	 * @param ansPath
	 *            生成图片路径
	 */
	@Override
	public String getLogoQRCode(String qrUrl, String ansPath) {
		// String filePath =
		// (javax.servlet.http.HttpServletRequest)request.getSession().getServletContext().getRealPath("/")
		// + "resources/images/logoImages/llhlogo.png";
		// filePath是二维码logo的路径，但是实际中我们是放在项目的某个路径下面的，所以路径用上面的，把下面的注释就好
		// String filePath = "E://mylogo.png";

		String content = qrUrl;
		try {
			String filePath = Thread.currentThread().getContextClassLoader()
					.getResource("/").getPath();
			filePath += "resourcesImg/mylogo.png";
			BufferedImage bim = getQR_CODEBufferedImage(content,
					BarcodeFormat.QR_CODE, 400, 400, getDecodeHintType());
			return addLogo_QRCode(bim, new File(filePath), ansPath);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return null;
	}

	/**
	 * 给二维码图片添加Logo
	 * 
	 * @param qrPic
	 * @param logoPic
	 */
	private String addLogo_QRCode(BufferedImage bim, File logoPic,
			String ansPath) {
		try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			/**
			 * 读取二维码图片，并构建绘图对象
			 */
			BufferedImage image = bim;
			Graphics2D g = image.createGraphics();

			/**
			 * 读取Logo图片
			 */
			BufferedImage logo = ImageIO.read(logoPic);
			/**
			 * 设置logo的大小,本人设置为二维码图片的20%,因为过大会盖掉二维码
			 */
			int widthLogo = logo.getWidth(null) > image.getWidth() * 3 / 11 ? (image
					.getWidth() * 3 / 11) : logo.getWidth(null), heightLogo = logo
					.getHeight(null) > image.getHeight() * 3 / 11 ? (image
					.getHeight() * 3 / 11) : logo.getWidth(null);

			/**
			 * logo放在中心
			 */
			int x = (image.getWidth() - widthLogo) / 2;
			int y = (image.getHeight() - heightLogo) / 2;
			/**
			 * logo放在右下角 int x = (image.getWidth() - widthLogo); int y =
			 * (image.getHeight() - heightLogo);
			 */

			// 开始绘制图片
			g.drawImage(logo, x, y, widthLogo, heightLogo, null);
			g.dispose();

			logo.flush();
			image.flush();
			baos.flush();
			ImageIO.write(image, "png", baos);

			// 二维码生成的路径，但是实际项目中，我们是把这生成的二维码显示到界面上的，因此下面的折行代码可以注释掉
			// 可以看到这个方法最终返回的是这个二维码的imageBase64字符串
			// 前端用 <img src="data:image/png;base64,${imageBase64QRCode}"/>
			// 其中${imageBase64QRCode}对应二维码的imageBase64字符串
			// 这种方法不知为何，页面不能显示

			String uuid = UUID.randomUUID().toString() + ".png";
			String imgId = ansPath + File.separator + uuid;
			ImageIO.write(image, "png", new File(imgId));

			// String imageBase64QRCode =
			// Base64.encodeBase64URLSafeString(baos.toByteArray());

			return uuid;
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成二维码bufferedImage图片
	 * 
	 * @param content
	 *            编码内容
	 * @param barcodeFormat
	 *            编码类型
	 * @param width
	 *            图片宽度
	 * @param height
	 *            图片高度
	 * @param hints
	 *            设置参数
	 * @return
	 */
	public BufferedImage getQR_CODEBufferedImage(String content,
			BarcodeFormat barcodeFormat, int width, int height,
			Map<EncodeHintType, ?> hints) {
		MultiFormatWriter multiFormatWriter = null;
		BitMatrix bm = null;
		BufferedImage image = null;
		try {
			multiFormatWriter = new MultiFormatWriter();
			// 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
			bm = multiFormatWriter.encode(content, barcodeFormat, width,
					height, hints);
			int w = bm.getWidth();
			int h = bm.getHeight();
			image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

			// 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					image.setRGB(x, y, bm.get(x, y) ? QRCOLOR : BGWHITE);
				}
			}
		} catch (WriterException e) {
			// e.printStackTrace();
		}
		return image;
	}

	/**
	 * 设置二维码的格式参数
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Map<EncodeHintType, Object> getDecodeHintType() {
		// 用于设置QR二维码参数
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		// 设置QR二维码的纠错级别（H为最高级别）具体级别信息
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// 设置编码方式
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hints.put(EncodeHintType.MARGIN, 1);
		hints.put(EncodeHintType.MAX_SIZE, 350);
		hints.put(EncodeHintType.MIN_SIZE, 100);

		return hints;
	}

}
