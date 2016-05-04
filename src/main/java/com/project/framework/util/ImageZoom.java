package com.project.framework.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * 图像缩放工具类
 * @author 王国斌
 */
public class ImageZoom {
	/**
	 * 按百分比缩放
	 * @param is
	 * @param percent
	 * @param extName
	 * @return
	 * @throws IOException
	 */
	public byte[] zoomByPercent(InputStream is, int percent, String extName) throws IOException {
		BufferedImage src = ImageIO.read(is);
		if (src == null) {
			is.close();
			throw new IOException("读取图片失败");
		}
		int width = src.getWidth();
		int height = src.getHeight();
		is.close();
		return zoom(src, (width * percent) / 100, (height * percent) / 100, extName);
	}

	public byte[] zoomByPercent(byte[] bytes, int percent, String extName) throws IOException {
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		return zoomByPercent(is, percent, extName);
	}

	public byte[] zoomByHeight(byte[] bytes, int height, String extName) throws IOException {
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		return zoomByHeight(is, height, extName);
	}

	public byte[] zoomByHeight(InputStream is, int h, String extName) throws IOException {
		BufferedImage src = ImageIO.read(is);
		if (src == null) {
			is.close();
			throw new IOException("读取图片失败");
		}
		int width = src.getWidth();
		int height = src.getHeight();
		is.close();
		return zoom(src, (int) (((float) h / height) * width), h, extName);
	}

	public byte[] zoomByWidth(byte[] bytes, int w, String extName) throws IOException {
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		return zoomByWidth(is, w, extName);
	}

	public byte[] zoomByWidth(InputStream is, int w, String extName) throws IOException {
		BufferedImage src = ImageIO.read(is);
		if (src == null) {
			is.close();
			throw new IOException("读取图片失败");
		}
		int width = src.getWidth();
		int height = src.getHeight();
		is.close();
		return zoom(src, w, (int) (((float) w / width) * height), extName);
	}

	public byte[] zoomByMaxWidthAndHeight(byte[] bytes, int w, int h, String extName) throws IOException {
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		return zoomByMaxWidthAndHeight(is, w, h, extName);
	}

	public byte[] zoomByMaxWidthAndHeight(InputStream is, int w, int h, String extName) throws IOException {
		BufferedImage src = ImageIO.read(is);
		if (src == null) {
			is.close();
			throw new IOException("读取图片失败");
		}
		int width = src.getWidth();
		int height = src.getHeight();
		int zoomHeight = (int) (((float) w / width) * height);
		boolean isZoomByHeight = false;
		if (zoomHeight > h) {
			isZoomByHeight = true;
		}
		if (isZoomByHeight) {
			w = (int) (((float) h / height) * width);
		} else {
			h = zoomHeight;
		}
		
		if (src.getWidth() <= w && src.getHeight() <= h) {
			w = src.getWidth();
			h = src.getHeight();
		}
		is.close();
		return zoom(src, w, h, extName);
	}

	private byte[] zoom(BufferedImage src, int w, int h, String extName) throws IOException {
		Image image = src.getScaledInstance(w, h, Image.SCALE_DEFAULT);
		BufferedImage tag = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB); // 缩放图像
		Graphics g = tag.getGraphics();
		g.drawImage(image, 0, 0, null); // 绘制缩小后的图
		g.dispose();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(tag, extName, bos);// 输出到bos
		byte[] bytes = bos.toByteArray();
		bos.close();
		return bytes;
	}
}
