package co.edu.uptc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ball {
    private int x;
    private int y;
    private int diameter;
    private int speedX;
    private int speedY;
    private int bounceCount;
}
