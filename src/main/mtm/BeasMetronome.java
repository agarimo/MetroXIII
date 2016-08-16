package main.mtm;

import javafx.application.Platform;
import main.Metronomo;
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.core.Bead;
import net.beadsproject.beads.data.Sample;
import net.beadsproject.beads.data.SampleManager;
import net.beadsproject.beads.ugens.Clock;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.SamplePlayer;

/**
 *
 * @author Agarimo
 */
public class BeasMetronome {

    private AudioContext ac;
    private Clock clock;
    private Gain gain;
    private Compas compas;
    private int tempo;
    private int count;

    private final Sample tick;
    private final Sample tack;
    private final String audioTick = "sounds/snd2.wav";
    private final String audioTack = "sounds/snd1.wav";

    public BeasMetronome() {
        count = 1;
        this.tempo = 100;
        this.compas = Compas.c4by4;
        tick = SampleManager.sample(audioTick);
        tack = SampleManager.sample(audioTack);
        init();
    }

    public BeasMetronome(int tempo, Compas compas) {
        count = 1;
        this.tempo = tempo;
        this.compas = compas;
        tick = SampleManager.sample(audioTick);
        tack = SampleManager.sample(audioTack);
        init();
    }

    private void init() {
        ac = new AudioContext();
        clock = new Clock(ac, getTimeLapse());
        clock.setTicksPerBeat(getNoteValue());
//        clock.setClick(true);
        clock.addMessageListener(new Bead() {
            @Override
            public void messageReceived(Bead message) {
                SamplePlayer player;

                if (count == 1) {
                    player = new SamplePlayer(ac, tick);
                    gain = new Gain(ac, 1, 0.5f);
                    showTick();
                } else {
                    player = new SamplePlayer(ac, tack);
                    gain = new Gain(ac, 1, 0.2f);
                    showTack();

                    if (count == compas.getCount()) {
                        count = 0;
                    }
                }
//                System.out.println(clock.getCount()+1);
                gain.addInput(player);
                ac.out.addInput(gain);
                count++;
            }
        });

        ac.out.addDependent(clock);
    }

    private void showTick() {
        Platform.runLater(() -> {
            Metronomo.label.setText("tick");
        });
    }

    private void showTack() {
        Platform.runLater(() -> {
            Metronomo.label.setText("tack");
        });
    }

    private long getTimeLapse() {
        return (60 * 1000) / this.tempo;
    }

    private int getNoteValue() {
        if (compas.getValue() == 4) {
            return 1;
        } else {
            return 2;
        }
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
        init();
    }

    public void setCompas(Compas compas) {
        this.compas = compas;
        init();
    }

    public void run() {
        ac.start();
    }

    public void stop() {
        ac.stop();
        Platform.runLater(() -> {
            Metronomo.label.setText("STOP");
        });
        count = 1;
    }

}
