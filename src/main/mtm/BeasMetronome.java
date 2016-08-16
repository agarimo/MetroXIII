package main.mtm;

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
    Clock clock;
    private Sample sample;
    private final String audioFile = "sounds/snd2.wav";
    private final int tempo;

    public BeasMetronome(int tempo) {
        this.tempo = tempo;
        init();
    }

    private void init() {
        sample = SampleManager.sample(audioFile);
        ac = new AudioContext();

        clock = new Clock(ac, getValue());
        clock.setTicksPerBeat(1);
        clock.setClick(true);

        clock.addMessageListener(new Bead() {
            @Override
            public void messageReceived(Bead message) {
//                System.out.println(clock.getCount()+1);
                SamplePlayer player = new SamplePlayer(ac, sample);
                Gain gain = new Gain(ac, 1, 0.2f);
                gain.addInput(player);
                ac.out.addInput(gain);
            }
        });

        ac.out.addDependent(clock);
    }

    private long getValue() {
        return (60 * 1000) / this.tempo;
    }

    public void run() {
        ac.start();

        System.out.println(
                "GAIN : " + ac.out.getGain());
        System.out.println(
                "TICKS PER BEAT : " + clock.getTicksPerBeat());
        System.out.println(
                "TEMPO : " + clock.getTempo());
    }

    public void stop() {
        ac.stop();
    }

}
