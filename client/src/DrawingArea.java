public class DrawingArea extends NodeSub {

	public static float depthBuffer[];
	public static int pixels[];
	public static int width;
	public static int height;
	public static int topY;
	public static int bottomY;
	public static int topX;
	public static int bottomX;
	public static int centerX;
	public static int centerY;
	public static int anInt1387;

	public static void initDrawingArea(int i, int j, int ai[], float depth[]) {
		depthBuffer = depth;
		pixels = ai;
		width = j;
		height = i;
		setDrawingArea(i, 0, j, 0);
	}

	public static void defaultDrawingAreaSize() {
		topX = 0;
		topY = 0;
		bottomX = width;
		bottomY = height;
		centerX = bottomX;
		centerY = bottomX / 2;
	}
	
    public static void fillCircle(int x, int y, int radius, int color) {
        int y1 = y - radius;
        if (y1 < 0) {
            y1 = 0;
        }
        int y2 = y + radius;
        if (y2 >= height) {
            y2 = height - 1;
        }
        for (int iy = y1; iy <= y2; iy++) {
            int dy = iy - y;
            int dist = (int) Math.sqrt(radius * radius - dy * dy);
            int x1 = x - dist;
            if (x1 < 0) {
                x1 = 0;
            }
            int x2 = x + dist;
            if (x2 >= width) {
                x2 = width - 1;
            }
            int pos = x1 + iy * width;
            for (int ix = x1; ix <= x2; ix++) {
                pixels[pos++] = color;
            }
        }
    }
	
	public static final void filterGrayscale(int x, int y, int width, int height, double amount) {
		if (amount <= 0) {
			return;
		}
        if (x < topX) {
            width -= topX - x;
            x = topX;
        }
        if (y < topY) {
            height -= topY - y;
            y = topY;
        }
        if (x + width > bottomX) {
            width = bottomX - x;
        }
        if (y + height > bottomY) {
            height = bottomY - y;
        }
        if (width <= 0 || height <= 0) {
        	return;
        }
        int pos = x + y * width;
        int offset = width - width;
        if (amount >= 1) {
            while (height-- > 0) {
                for (int i = 0; i < width; i++) {
                	final int red = pixels[pos] >> 16 & 0xff;
                	final int green = pixels[pos] >> 8 & 0xff;
                	final int blue = pixels[pos] & 0xff;
                    final int lightness = (red + green + blue) / 3;
                    final int color = lightness << 16 | lightness << 8 | lightness;
                    pixels[pos++] = color;
                }
                pos += offset;
            }
        } else {
        	final double divider = 2 * amount + 1;
            while (height-- > 0) {
                for (int i = 0; i < width; i++) {
                	int red = pixels[pos] >> 16 & 0xff;
                	int green = pixels[pos] >> 8 & 0xff;
                	int blue = pixels[pos] & 0xff;
                    final int red2 = (int) (red * amount);
                    final int green2 = (int) (green * amount);
                    final int blue2 = (int) (blue * amount);
                    red = (int) ((red + green2 + blue2) / divider);
                    green = (int) ((red2 + green + blue2) / divider);
                    blue = (int) ((red2 + green2 + blue) / divider);
                    final int color = red << 16 | green << 8 | blue;
                    pixels[pos++] = color;
                }
                pos += offset;
            }
        }
	}
	
	public static void drawHorizontalLine(int drawX, int drawY, int lineWidth, int i_62_) {
        if (drawY >= topY && drawY < bottomY) {
            if (drawX < topX) {
                lineWidth -= topX - drawX;
                drawX = topX;
            }
            if (drawX + lineWidth > bottomX) {
                lineWidth = bottomX - drawX;
            }
            int i_63_ = drawX + drawY * width;
            for (int i_64_ = 0; i_64_ < lineWidth; i_64_++) {
                pixels[i_63_ + i_64_] = i_62_;
            }
        }
    }	

	public static void setDrawingArea(int i, int j, int k, int l) {
		if(j < 0) {
			j = 0;
		}
		if(l < 0) {
			l = 0;
		}
		if(k > width) {
			k = width;
		}
		if(i > height) {
			i = height;
		}
		topX = j;
		topY = l;
		bottomX = k;
		bottomY = i;
		centerX = bottomX;
		centerY = bottomX / 2;
		anInt1387 = bottomY / 2;
	}

	public static void setAllPixelsToZero() {
		int i = width * height;
		for(int j = 0; j < i; j++) {
			pixels[j] = 0; 
			depthBuffer[j] = Float.MAX_VALUE;
		}
	}
	
	public static void method336(int i, int j, int k, int l, int i1) {
		if (k < topX) {
			i1 -= topX - k;
			k = topX;
		}
		if (j < topY) {
			i -= topY - j;
			j = topY;
		}
		if (k + i1 > bottomX)
			i1 = bottomX - k;
		if (j + i > bottomY)
			i = bottomY - j;
		int k1 = width - i1;
		int l1 = k + j * width;
		for (int i2 = -i; i2 < 0; i2++) {
			for (int j2 = -i1; j2 < 0; j2++)
				pixels[l1++] = l;

			l1 += k1;
		}

	}	

	public static void method335(int i, int j, int k, int l, int i1, int k1)
	{
		if(k1 < topX)
		{
			k -= topX - k1;
			k1 = topX;
		}
		if(j < topY)
		{
			l -= topY - j;
			j = topY;
		}
		if(k1 + k > bottomX)
			k = bottomX - k1;
		if(j + l > bottomY)
			l = bottomY - j;
		int l1 = 256 - i1;
		int i2 = (i >> 16 & 0xff) * i1;
		int j2 = (i >> 8 & 0xff) * i1;
		int k2 = (i & 0xff) * i1;
		int k3 = width - k;
		int l3 = k1 + j * width;
		for(int i4 = 0; i4 < l; i4++)
		{
			for(int j4 = -k; j4 < 0; j4++)
			{
				int l2 = (pixels[l3] >> 16 & 0xff) * l1;
				int i3 = (pixels[l3] >> 8 & 0xff) * l1;
				int j3 = (pixels[l3] & 0xff) * l1;
				int k4 = ((i2 + l2 >> 8) << 16) + ((j2 + i3 >> 8) << 8) + (k2 + j3 >> 8);
				pixels[l3++] = k4;
			}

			l3 += k3;
		}
	}
	
	public static void drawAlphaPixels(int x, int y, int w, int h, int color, int alpha) {
		if (x < topX) {
			w -= topX - x;
			x = topX;
		}
		if (y < topY) {
			h -= topY - y;
			y = topY;
		}
		if (x + w > bottomX)
			w = bottomX - x;
		if (y + h > bottomY)
			h = bottomY - y;
		int l1 = 256 - alpha;
		int i2 = (color >> 16 & 0xff) * alpha;
		int j2 = (color >> 8 & 0xff) * alpha;
		int k2 = (color & 0xff) * alpha;
		int k3 = width - w;
		int l3 = x + y * width;
		for (int i4 = 0; i4 < h; i4++) {
			for (int j4 = -w; j4 < 0; j4++) {
				int l2 = (pixels[l3] >> 16 & 0xff) * l1;
				int i3 = (pixels[l3] >> 8 & 0xff) * l1;
				int j3 = (pixels[l3] & 0xff) * l1;
				int k4 = ((i2 + l2 >> 8) << 16) + ((j2 + i3 >> 8) << 8)
						+ (k2 + j3 >> 8);
				pixels[l3++] = k4;
			}

			l3 += k3;
		}
	}
	
	public static void drawPixels(int i, int j, int k, int l, int i1) {
		if (k < topX) {
			i1 -= topX - k;
			k = topX;
		}
		if (j < topY) {
			i -= topY - j;
			j = topY;
		}
		if (k + i1 > bottomX)
			i1 = bottomX - k;
		if (j + i > bottomY)
			i = bottomY - j;
		int k1 = width - i1;
		int l1 = k + j * width;
		for (int i2 = -i; i2 < 0; i2++) {
			for (int j2 = -i1; j2 < 0; j2++)
				pixels[l1++] = l;

			l1 += k1;
		}

	}
	
	public static void drawAlphaGradient(int x, int y, int gradientWidth, int gradientHeight, int startColor, int endColor, int alpha) {
		int k1 = 0;
		int l1 = 0x10000 / gradientHeight;
		if(x < topX) {
			gradientWidth -= topX - x;
			x = topX;
		}
		if(y < topY) {
			k1 += (topY - y) * l1;
			gradientHeight -= topY - y;
			y = topY;
		}
		if(x + gradientWidth > bottomX)
			gradientWidth = bottomX - x;
		if(y + gradientHeight > bottomY)
			gradientHeight = bottomY - y;
		int i2 = width - gradientWidth;
		int result_alpha = 256 - alpha;
		int total_pixels = x + y * width;
		for(int k2 = -gradientHeight; k2 < 0; k2++) {
			int gradient1 = 0x10000 - k1 >> 8;
			int gradient2 = k1 >> 8;
			int gradient_color = ((startColor & 0xff00ff) * gradient1 + (endColor & 0xff00ff) * gradient2 & 0xff00ff00) + ((startColor & 0xff00) * gradient1 + (endColor & 0xff00) * gradient2 & 0xff0000) >>> 8;
			int color = ((gradient_color & 0xff00ff) * alpha >> 8 & 0xff00ff) + ((gradient_color & 0xff00) * alpha >> 8 & 0xff00);
			for(int k3 = -gradientWidth; k3 < 0; k3++) {
				int colored_pixel = pixels[total_pixels];
				colored_pixel = ((colored_pixel & 0xff00ff) * result_alpha >> 8 & 0xff00ff) + ((colored_pixel & 0xff00) * result_alpha >> 8 & 0xff00);
				pixels[total_pixels++] = color + colored_pixel;
			}
			total_pixels += i2;
			k1 += l1;
		}
	}
	
	public static void drawAlphaHorizontalLine(int x, int y, int lineWidth, int color, int alpha) {
		if (y < topY || y >= bottomY)
			return;
		if (x < topX) {
			lineWidth -= topX - x;
			x = topX;
		}
		if (x + lineWidth > bottomX)
			lineWidth = bottomX - x;
		int i3 = x + y * width;
		for (int j3 = 0; j3 < lineWidth; j3++) {
			int alpha2 = (lineWidth-j3)/(lineWidth/alpha);
			int j1 = 256 - alpha2;
			int k1 = (color >> 16 & 0xff) * alpha2;
			int l1 = (color >> 8 & 0xff) * alpha2;
			int i2 = (color & 0xff) * alpha2;
			int j2 = (pixels[i3] >> 16 & 0xff) * j1;
			int k2 = (pixels[i3] >> 8 & 0xff) * j1;
			int l2 = (pixels[i3] & 0xff) * j1;
			int k3 = ((k1 + j2 >> 8) << 16) + ((l1 + k2 >> 8) << 8) + (i2 + l2 >> 8);
			pixels[i3++] = k3;
		}
	}

	public static void fillPixels(int i, int j, int k, int l, int i1)
	{
		method339(i1, l, j, i);
		method339((i1 + k) - 1, l, j, i);
		method341(i1, l, k, i);
		method341(i1, l, k, (i + j) - 1);
	}

	public static void method338(int i, int j, int k, int l, int i1, int j1)
	{
		method340(l, i1, i, k, j1);
		method340(l, i1, (i + j) - 1, k, j1);
		if(j >= 3)
		{
			method342(l, j1, k, i + 1, j - 2);
			method342(l, (j1 + i1) - 1, k, i + 1, j - 2);
		}
	}

	public static void method339(int i, int j, int k, int l)
	{
		if(i < topY || i >= bottomY)
			return;
		if(l < topX)
		{
			k -= topX - l;
			l = topX;
		}
		if(l + k > bottomX)
			k = bottomX - l;
		int i1 = l + i * width;
		for(int j1 = 0; j1 < k; j1++)
			pixels[i1 + j1] = j;

	}

	public static void method340(int i, int j, int k, int l, int i1)
	{
		if(k < topY || k >= bottomY)
			return;
		if(i1 < topX)
		{
			j -= topX - i1;
			i1 = topX;
		}
		if(i1 + j > bottomX)
			j = bottomX - i1;
		int j1 = 256 - l;
		int k1 = (i >> 16 & 0xff) * l;
		int l1 = (i >> 8 & 0xff) * l;
		int i2 = (i & 0xff) * l;
		int i3 = i1 + k * width;
		for(int j3 = 0; j3 < j; j3++)
		{
			int j2 = (pixels[i3] >> 16 & 0xff) * j1;
			int k2 = (pixels[i3] >> 8 & 0xff) * j1;
			int l2 = (pixels[i3] & 0xff) * j1;
			int k3 = ((k1 + j2 >> 8) << 16) + ((l1 + k2 >> 8) << 8) + (i2 + l2 >> 8);
			pixels[i3++] = k3;
		}

	}

	public static void method341(int i, int j, int k, int l)
	{
		if(l < topX || l >= bottomX)
			return;
		if(i < topY)
		{
			k -= topY - i;
			i = topY;
		}
		if(i + k > bottomY)
			k = bottomY - i;
		int j1 = l + i * width;
		for(int k1 = 0; k1 < k; k1++)
			pixels[j1 + k1 * width] = j;

	}

	private static void method342(int i, int j, int k, int l, int i1) {
		if(j < topX || j >= bottomX)
			return;
		if(l < topY) {
			i1 -= topY - l;
			l = topY;
		}
		if(l + i1 > bottomY)
			i1 = bottomY - l;
		int j1 = 256 - k;
		int k1 = (i >> 16 & 0xff) * k;
		int l1 = (i >> 8 & 0xff) * k;
		int i2 = (i & 0xff) * k;
		int i3 = j + l * width;
		for(int j3 = 0; j3 < i1; j3++) {
			int j2 = (pixels[i3] >> 16 & 0xff) * j1;
			int k2 = (pixels[i3] >> 8 & 0xff) * j1;
			int l2 = (pixels[i3] & 0xff) * j1;
			int k3 = ((k1 + j2 >> 8) << 16) + ((l1 + k2 >> 8) << 8) + (i2 + l2 >> 8);
			pixels[i3] = k3;
			i3 += width;
		}
	}
	
	public static void drawRectangle(int x, int y, int width, int height, int color, int alpha) {
		drawHorizontalLine(x, y, width, color, alpha);
		drawHorizontalLine(x, y + height - 1, width, color, alpha);
		if(height >= 3) {
			drawVerticalLine(x, y + 1, height - 2, color, alpha);
			drawVerticalLine(x + width - 1, y + 1, height - 2, color, alpha);
		}
	}
	
	public static void fillRectangle(int x, int y, int width, int height, int color, int alpha) {
		if(x < topX) {
			width -= topX - x;
			x = topX;
		}
		if(y < topY) {
			height -= topY - y;
			y = topY;
		}
		if(x + width > bottomX) {
			width = bottomX - x;
		}
		if(y + height > bottomY) {
			height = bottomY - y;
		}
		final int l1 = 256 - alpha;
		final int i2 = (color >> 16 & 0xff) * alpha;
		final int j2 = (color >> 8 & 0xff) * alpha;
		final int k2 = (color & 0xff) * alpha;
		final int k3 = width - width;
		int l3 = x + y * width;
		for(int i4 = 0; i4 < height; i4++) {
			for(int j4 = -width; j4 < 0; j4++) {
				final int l2 = (pixels[l3] >> 16 & 0xff) * l1;
				final int i3 = (pixels[l3] >> 8 & 0xff) * l1;
				final int j3 = (pixels[l3] & 0xff) * l1;
				final int k4 = (i2 + l2 >> 8 << 16) + (j2 + i3 >> 8 << 8) + (k2 + j3 >> 8);
				pixels[l3++] = k4;
			}
			l3 += k3;
		}
	}
	
	public static void drawHorizontalLine(int x, int y, int length, int color, int alpha) {
		if(y < topY || y >= bottomY) {
			return;
		}
		if(x < topX) {
			length -= topX - x;
			x = topX;
		}
		if(x + length > bottomX) {
			length = bottomX - x;
		}
		final int j1 = 256 - alpha;
		final int k1 = (color >> 16 & 0xff) * alpha;
		final int l1 = (color >> 8 & 0xff) * alpha;
		final int i2 = (color & 0xff) * alpha;
		int i3 = x + y * width;
		for(int j3 = 0; j3 < length; j3++) {
			final int j2 = (pixels[i3] >> 16 & 0xff) * j1;
			final int k2 = (pixels[i3] >> 8 & 0xff) * j1;
			final int l2 = (pixels[i3] & 0xff) * j1;
			final int k3 = (k1 + j2 >> 8 << 16) + (l1 + k2 >> 8 << 8) + (i2 + l2 >> 8);
			pixels[i3++] = k3;
		}
	}
	
	public static void drawVerticalLine(int x, int y, int length, int color, int alpha) {
		if(x < topX || x >= bottomX) {
			return;
		}
		if(y < topY) {
			length -= topY - y;
			y = topY;
		}
		if(y + length > bottomY) {
			length = bottomY - y;
		}
		final int j1 = 256 - alpha;
		final int k1 = (color >> 16 & 0xff) * alpha;
		final int l1 = (color >> 8 & 0xff) * alpha;
		final int i2 = (color & 0xff) * alpha;
		int i3 = x + y * width;
		for(int j3 = 0; j3 < length; j3++) {
			final int j2 = (pixels[i3] >> 16 & 0xff) * j1;
			final int k2 = (pixels[i3] >> 8 & 0xff) * j1;
			final int l2 = (pixels[i3] & 0xff) * j1;
			final int k3 = (k1 + j2 >> 8 << 16) + (l1 + k2 >> 8 << 8) + (i2 + l2 >> 8);
			pixels[i3] = k3;
			i3 += width;
		}
	}
}