package main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HytaleMandelbrotGenerator {
    public static void main(String[] args) throws IOException {
        int width = 1024;
        int height = 1024;
        int maxIter = 69;

        // Complex plane window: tweak these to zoom/shift
        double minRe = -2.0;
        double maxRe = 1.0;
        double minIm = -1.5;
        double maxIm = 1.5;

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < height; y++) {
            double c_im = maxIm - y * (maxIm - minIm) / (height - 1);
            for (int x = 0; x < width; x++) {
                double c_re = minRe + x * (maxRe - minRe) / (width - 1);

                double z_re = 0.0;
                double z_im = 0.0;
                int iter = 0;

                // Iterate z = z^2 + c
                while (z_re * z_re + z_im * z_im <= 4.0 && iter < maxIter) {
                    double zr2 = z_re * z_re;
                    double zi2 = z_im * z_im;
                    double twoReIm = 2.0 * z_re * z_im;

                    z_re = zr2 - zi2 + c_re;
                    z_im = twoReIm + c_im;
                    iter++;
                }

                double value; // 0.0..1.0 where 1 => white (fast escape), 0 => black (inside/slow)

                if (iter >= maxIter) {
                    // interior of the set -> pure black
                    value = 0.0;
                } else {
                    // smooth (continuous) escape time
                    double zAbs = Math.sqrt(z_re * z_re + z_im * z_im);
                    // protect against numerical issues (shouldn't happen since escaped)
                    if (zAbs <= 0.0) zAbs = 1e-10;

                    // mu = iter + 1 - log(log(|z|)) / log(2)
                    double mu = iter + 1 - Math.log(Math.log(zAbs)) / Math.log(2);

                    // normalized t in [0,1]
                    double t = mu / maxIter;
                    if (t < 0.0) t = 0.0;
                    if (t > 1.0) t = 1.0;

                    // invert so small iter -> white (1.0), large iter -> dark
                    value = 1.0 - t;
                }

                // optional contrast boost (uncomment if you want stronger midtones)
                // value = Math.pow(value, 0.9);

                int gray = (int) Math.round(255.0 * value);
                if (gray < 0) gray = 0;
                if (gray > 255) gray = 255;

                int rgb = (gray << 16) | (gray << 8) | gray;
                img.setRGB(x, y, rgb);
            }
        }

        File out = new File("mandelbrot_1024.png");
        ImageIO.write(img, "png", out);
        System.out.println("Wrote " + out.getAbsolutePath());
    }
}
