// MainActivity.java
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView2;
    private Button[][] buttons = new Button[3][3];
    private boolean playerTurn = true; // true - X, false - O
    private int plays = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TableLayout tableLayout = findViewById(R.id.tableLayout);

        // Инициализация кнопок
        for (int i = 0; i < 3; i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            for (int j = 0; j < 3; j++) {
                final int finalI = i;
                final int finalJ = j;
                buttons[i][j] = (Button) row.getChildAt(j);
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onButtonClicked(finalI, finalJ);
                    }
                });
            }
        }

        Button resetButton = findViewById(R.id.button12);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBoard();
            }
        });
    }

    // Обработка нажатия кнопки
    private void onButtonClicked(int x, int y) {
        textView2=findViewById(R.id.textView2);
        Button button = buttons[x][y];
        if (button.getText().toString().isEmpty()) {
            if (playerTurn) {
                button.setText("X");
            } else {
                button.setText("O");
            }
            plays++;

            if (checkWin(x, y)) {
                String winner = playerTurn ? "Игрок X" : "Игрок O";
                Toast.makeText(this, winner + " выйграл", Toast.LENGTH_SHORT).show();
                resetBoard();
                textView2.setText("Выйграл " + winner);
            } else if (plays == 9) {
                Toast.makeText(this, "Ничья", Toast.LENGTH_SHORT).show();
                resetBoard();
                textView2.setText("Ничья");
            } else {
                playerTurn = !playerTurn;

            }
        }
    }

    // Проверка на победу
    private boolean checkWin(int x, int y) {
        String symbol = playerTurn ? "X" : "O";

        // Check horizontally
        for (int i = 0; i < 3; i++) {
            if (!buttons[i][y].getText().toString().equals(symbol)) {
                break;
            }
            if (i == 2) {
                return true;
            }
        }

        // Check vertically
        for (int j = 0; j < 3; j++) {
            if (!buttons[x][j].getText().toString().equals(symbol)) {
                break;
            }
            if (j == 2) {
                return true;
            }
        }

        // Check diagonally
        if (x == y) {
            for (int i = 0; i < 3; i++) {
                if (!buttons[i][i].getText().toString().equals(symbol)) {
                    break;
                }
                if (i == 2) {
                    return true;
                }
            }
        }

        // Check reverse diagonally
        if (x + y == 2) {
            for (int i = 0; i < 3; i++) {
                if (!buttons[i][2 - i].getText().toString().equals(symbol)) {
                    break;
                }
                if (i == 2) {
                    return true;
                }
            }
        }

        return false;
    }

    // Сброс игрового поля
    private void resetBoard() {
        textView2=findViewById(R.id.textView2);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        playerTurn = true;
        plays = 0;
        textView2.setText(" ");
    }
}
