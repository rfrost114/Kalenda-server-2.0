/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packages;

import java.util.Arrays;

/**
 *
 * @author Richie Frost
 */

import java.io.Serializable;

public class WeekAvail implements Serializable{
    

    private Day[] week;
    
    public WeekAvail() {
        

        week = new Day[7];
        int i = 0;
        for (DayNames dayName : DayNames.values()) {
            week[i] = new Day(dayName);
            i++;
        }
        
    }
    
    public Day[] getWeek() {
        return week;
    }
    
    public Day getDay(DayNames dayName) {
        for (Day day : week) {
            if (day.getDayName() == dayName) {
                return day;
            }
        }
        return null;
    }
    
    public void report() {
        for (Day day : week) {
            System.out.println(Arrays.toString(day.getTimes()));
        
        }
    
    }
    
    
    
    
    
}
