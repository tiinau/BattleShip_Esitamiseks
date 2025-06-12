package models;

import java.time.LocalDateTime;

public class ScoreData implements Comparable<ScoreData>{
    private String name;
    private int time; // Mängu aeg sekundites
    private int clicks;
    private int board;
    private LocalDateTime playedTime;

    public ScoreData(String name, int time, int clicks, int board, LocalDateTime playedTime) {
        this.name = name;
        this.time = time;
        this.clicks = clicks;
        this.board = board;
        this.playedTime = playedTime;
    }

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    public int getClicks() {
        return clicks;
    }

    public int getBoard() {
        return board;
    }

    public LocalDateTime getPlayedTime() {
        return playedTime;

    }

    @Override
    public String toString() { // Näitab infot ühel real
        return "ScoreData{" +
                "name='" + name + '\'' +
                ", time=" + time +
                ", clicks=" + clicks +
                ", board=" + board +
                ", playedTime=" + playedTime +
                '}';
    }


    @Override
    public int compareTo(ScoreData o) {
        // Küsime muujal: Collections.sort(Massiivinimi)
       int cmp = Integer.compare(this.time, o.time);
       if(cmp !=0) return cmp;

       cmp = Integer.compare(this.clicks, o.clicks);
       if (cmp !=0) return cmp;

       return this.playedTime.compareTo(o.playedTime);
    }

    /**
     * Vormindab etteantuda aja kujule MM:SS
     * @param seconds sekundid kujul 98
     * @return vormindatud aeg
     */
    public String formatGameTime(int seconds){
        int min = seconds / 60;
        int sec = seconds % 60;
        return String.format("%02d:%02d", min, sec);
    }
}
