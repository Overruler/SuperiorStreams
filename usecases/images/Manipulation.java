package images;

import java.io.IOException;
import utils.streams.HSBStream;
import utils.streams.RGBStream;
import utils.streams.Streams;

public class Manipulation {

	public static void main(String[] args) {
		try {
			RGBStream stream = Streams.loadImageInRGB("pagoda.jpg");
			stream.swapRedAndGreen().save("pagoda_rg.jpg");
			stream.swapRedAndBlue().save("pagoda_rb.jpg");
			stream.swapGreenAndBlue().save("pagoda_gb.jpg");
			stream.swapAlphaAndBlue().save("pagoda_ab.png");
			stream.setRed(255).save("pagoda_bright_red.png");
			stream.gammaExpand(2.4).save("pagoda_texture.jpg");
			HSBStream bw = stream.toBlackAndWhite();
			bw.save("pagoda_bw.jpg");
			bw.gammaCompress(0.5).save("pagoda_bw_05.jpg");
			blackAndWhite(bw, 1.0);
			blackAndWhite(bw, 1.5);
			blackAndWhite(bw, 2.0);
			blackAndWhite(bw, 2.4);
			blackAndWhite(bw, 3.0);
			blackAndWhite(bw, 4.0);
			blackAndWhite(bw, 6.0);
			blackAndWhite(bw, 8.0);
			HSBStream hsb = Streams.loadImageInHSB("pagoda.jpg");
			hsb.mapBrightness((h, s, b, a) -> b * 0.25).save("pagoda_dark.jpg");
			hsb.mapHue((h, s, b, a) -> h + 10).save("pagoda_altered_hue.jpg");
			hsb.mapHue((h, s, b, a) -> 30).mapSaturation((h, s, b, a) -> s * 0.682 + 0.1).save("pagoda_sepia.jpg");
			RGBStream rgb = hsb.mapSaturation((h, s, b, a) -> s * 0.02).mapBrightness((h, s, b, a) -> b * 0.8).toRGB();
			sepia1(rgb, 0.4, 0.1);
			sepia1(rgb, 0.4, 0.2);
			sepia1(rgb, 0.4, 0.3);
			sepia2(rgb, 0.9, 0.1);
			sepia2(rgb, 0.8, 0.2);
			sepia2(rgb, 0.7, 0.3);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	private static void blackAndWhite(HSBStream bw, double gamma) throws IOException {
		String path = "pagoda_bw_" + String.valueOf(gamma).replace(".", "") + ".jpg";
		bw.gammaCompress(gamma).save(path);
	}
	private static void sepia1(RGBStream rgb, double d, double e) throws IOException {
		String path = "pagoda_sepia1_" + d + "_" + e + ".jpg";
		rgb.mapBlue((a, r, g, b) -> (int) (b - (b / 255.0 - d) * e * 255.0)).mapRed(
		  (a, r, g, b) -> (int) (r + (r / 255.0 - d) * e * 255.0)).save(path);
	}
	private static void sepia2(RGBStream rgb, double e, double d) throws IOException {
		String path = "pagoda_sepia2_" + d + "_" + e + ".jpg";
		rgb.mapBlue((a, r, g, b) -> (int) (b * e)).mapRed((a, r, g, b) -> (int) (r * e + d * 255.0)).save(path);
	}
}
