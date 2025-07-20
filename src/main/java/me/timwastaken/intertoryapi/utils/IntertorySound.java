package me.timwastaken.intertoryapi.utils;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class IntertorySound {
    public static IntertorySound FAIL = new IntertorySound(Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
    public static IntertorySound SUCCESS = new IntertorySound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 2f);

    public static IntertorySound createCustomSound(Sound sound, float volume, float pitch) {
        return new IntertorySound(sound, volume, pitch);
    }

    private final Sound sound;
    private final float volume;
    private final float pitch;

    private IntertorySound(Sound sound, float volume, float pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public void playTo(Player p) {
        p.playSound(p, this.sound, this.volume, this.pitch);
    }
}
