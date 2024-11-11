package net.minecraft.client.renderer;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
class ThreadDownloadImage extends Thread
{
    /** The URL of the image to download. */
    final String location;

    /** The image buffer to use. */
    final IImageBuffer buffer;

    /** The image data. */
    final ThreadDownloadImageData imageData;

    ThreadDownloadImage(ThreadDownloadImageData par1, String par2Str, IImageBuffer par3IImageBuffer)
    {
        this.imageData = par1;
        this.location = par2Str;
        this.buffer = par3IImageBuffer;
    }

    public void run() {
        HttpURLConnection var1 = null;

        try {
            URL var2 = new URL(this.location);
            var1 = (HttpURLConnection) var2.openConnection();
            var1.setDoInput(true);
            var1.setDoOutput(false);

            // Tentative de connexion
            if (var1.getResponseCode() / 100 == 4) {
                System.out.println("Erreur 404 ou 403 : URL des skins inaccessible.");
                return;
            }

            // Charger le skin si possible
            if (this.buffer == null) {
                this.imageData.image = ImageIO.read(var1.getInputStream());
            } else {
                this.imageData.image = this.buffer.parseUserSkin(ImageIO.read(var1.getInputStream()));
            }
        } catch (UnknownHostException uhe) {
            System.out.println("URL des skins introuvable, téléchargement annulé.");
            // Si cette exception survient, on sort simplement de la méthode pour éviter les erreurs répétées
        } catch (Exception var6) {
            // Autres exceptions non liées à l'URL introuvable
            var6.printStackTrace();
        } finally {
            if (var1 != null) {
                var1.disconnect();
            }
        }
    }

}
