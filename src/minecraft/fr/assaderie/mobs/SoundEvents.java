package fr.assaderie.mobs;

import java.net.URL;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class SoundEvents {

    @ForgeSubscribe
    public void onSound(SoundLoadEvent event) {
        try {
            // Sons pour les chiens
            addSound(event, "dog/death.ogg");
            addSound(event, "dog/hurt1.ogg");
            addSound(event, "dog/hurt2.ogg");
            addSound(event, "dog/hurt3.ogg");
            addSound(event, "dog/say1.ogg");
            addSound(event, "dog/say2.ogg");
            addSound(event, "dog/say3.ogg");
            addSound(event, "dog/say4.ogg");
            addSound(event, "dog/step1.ogg");
            addSound(event, "dog/step2.ogg");
            addSound(event, "dog/step3.ogg");
            addSound(event, "dog/dogattack1.ogg");
            addSound(event, "dog/dogattack2.ogg");
            addSound(event, "dog/dogattack3.ogg");
            addSound(event, "dog/bark1.ogg");
            addSound(event, "dog/bark2.ogg");
            addSound(event, "dog/bark3.ogg");
            addSound(event, "dog/bark4.ogg");
            addSound(event, "dog/bark5.ogg");

            // Sons pour les loups
            addSound(event, "wolf/death.ogg");
            addSound(event, "wolf/hurt1.ogg");
            addSound(event, "wolf/hurt2.ogg");
            addSound(event, "wolf/hurt3.ogg");
            addSound(event, "wolf/say1.ogg");
            addSound(event, "wolf/say2.ogg");
            addSound(event, "wolf/say3.ogg");
            addSound(event, "wolf/say4.ogg");
            addSound(event, "wolf/step1.ogg");
            addSound(event, "wolf/step2.ogg");
            addSound(event, "wolf/step3.ogg");
            addSound(event, "wolf/attack1.ogg");
            addSound(event, "wolf/attack2.ogg");
            addSound(event, "wolf/attack3.ogg");
            addSound(event, "wolf/wolfhowl1.ogg");
            addSound(event, "wolf/wolfhowl2.ogg");
            addSound(event, "wolf/wolfhowl3.ogg");


            // Sons pour les zombies
            addSound(event, "zombie/death.ogg");
            addSound(event, "zombie/hurt1.ogg");
            addSound(event, "zombie/hurt2.ogg");
            addSound(event, "zombie/say1.ogg");
            addSound(event, "zombie/say2.ogg");
            addSound(event, "zombie/say3.ogg");
            addSound(event, "zombie/step1.ogg");
            addSound(event, "zombie/step2.ogg");
            addSound(event, "zombie/step3.ogg");
            addSound(event, "zombie/stefp4.ogg");
            addSound(event, "zombie/step5.ogg");

        } catch (Exception e) {
            System.err.println("Cannot load sounds from Assaderie Mobs: " + e.getMessage());
        }
    }

    private void addSound(SoundLoadEvent event, String soundFile) throws Exception {
        String path = "sounds/" + soundFile;
        URL soundURL = AssaderieMobs.class.getResource(path);
        if (soundURL == null) {
            System.err.println("Sound file not found: " + path);
        } else {
            event.manager.soundPoolSounds.addSound(soundFile, soundURL);
        }
    }
}
