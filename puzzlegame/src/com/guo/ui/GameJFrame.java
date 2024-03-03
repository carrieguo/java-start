package com.guo.ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Console;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {
    //与游戏相关的代码
    //二维数组用来记录图片位置
    int[][] data = new int[4][4];
    //保存空白图片的位置
    int x = 0;
    int y = 0;

    String path = "puzzlegame/image/animal/animal1";

    //表示游戏胜利位置的二维数组
    int[][] win = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};

    //记录游戏的步数
    int step = 0;
    //创建选项下面的条目对象
    JMenuItem replayItem = new JMenuItem("重新游戏");
    JMenuItem reLoginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");
    JMenuItem accountItem = new JMenuItem("公众号");
    //更换图片条目
    JMenuItem girlItem = new JMenuItem("美女");
    JMenuItem animalItem = new JMenuItem("动物");
    JMenuItem sportsItem = new JMenuItem("运动");

    public GameJFrame() {
        //初始化界面
        initJFrame();
        //初始化菜单
        initJMenuBar();

        //打乱图片顺序
        initData();
        //初始化图片
        initImage();
        //显示出来
        this.setVisible(true);
    }

    private void initData() {
        int[] tempArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        Random r = new Random();
        for (int i = 0; i < tempArr.length; i++) {
            int index = r.nextInt(tempArr.length);
            int temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;
        }

        for (int i = 0; i < tempArr.length; i++) {
            if (tempArr[i] == 0) {
                x = i / 4;
                y = i % 4;
            }
            data[i / 4][i % 4] = tempArr[i];
        }
    }

    private void initImage() {
        //清空原本已经出现的所有图片
        this.getContentPane().removeAll();
        //先加载的图片在上方，后加载的图片在下面

        //步数
        JLabel stepCount = new JLabel("步数：" + step);
        stepCount.setBounds(50, 30, 100, 20);
        this.getContentPane().add(stepCount);

        //胜利图标
        if (victory()) {
            JLabel victoryIcon = new JLabel(new ImageIcon("puzzlegame/image/win.png"));
            victoryIcon.setBounds(203, 283, 197, 73);
            this.getContentPane().add(victoryIcon);
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                //创建一个图片ImageIcon的对象
                ImageIcon imageIcon = new ImageIcon(path + "/" + data[i][j] + ".jpg");
                //创建一个JLabel的对象（管理容器）
                JLabel jLabel = new JLabel(imageIcon);
                //指定图片位置
                jLabel.setBounds(105 * j + 83, 105 * i + 134, 105, 105);
                //给图片添加边框
                jLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
                //把管理容器添加到界面中
                this.getContentPane().add(jLabel);

            }

        }

        JLabel background = new JLabel(new ImageIcon("puzzlegame/image/background.png"));
        background.setBounds(40, 40, 508, 560);
        this.getContentPane().add(background);

        //刷新一下界面
        this.getContentPane().repaint();


    }

    private void initJMenuBar() {
        //初始化菜单
        //创建整个的菜单对象
        JMenuBar jMenuBar = new JMenuBar();
        //创建菜单上面的两个选项的对象（功能 关于我们）
        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutJMenu = new JMenu("关于我们");
        JMenu changeImgJmenu = new JMenu("更换图片");

        //将每一个选项下面的条目添加到选项当中
        changeImgJmenu.add(girlItem);
        changeImgJmenu.add(animalItem);
        changeImgJmenu.add(sportsItem);
        functionJMenu.add(changeImgJmenu);

        functionJMenu.add(replayItem);
        functionJMenu.add(reLoginItem);
        functionJMenu.add(closeItem);

        aboutJMenu.add(accountItem);


        //给条目绑定事件
        replayItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);
        aboutJMenu.addActionListener(this);

        girlItem.addActionListener(this);
        animalItem.addActionListener(this);
        sportsItem.addActionListener(this);

        //将菜单里面的两个选项添加到菜单当中
        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutJMenu);
        //给整个界面设置菜单
        this.setJMenuBar(jMenuBar);
    }

    private void initJFrame() {
        this.setSize(488, 430);
        //设置界面的标题
        this.setTitle("拼图 单机版");
        //设置界面置顶
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //取消默认的布局方式
        this.setLayout(null);
        //给整个界面添加键盘监听事件
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //按键盘a键显示正确图片
        if (e.getKeyCode() == 65) {
            this.getContentPane().removeAll();
            JLabel jLabel = new JLabel(new ImageIcon(path + "/all.jpg"));
            jLabel.setBounds(83, 134, 420, 420);
            this.getContentPane().add(jLabel);

            JLabel background = new JLabel(new ImageIcon("puzzlegame/image/background.png"));
            background.setBounds(40, 40, 508, 560);
            this.getContentPane().add(background);
            this.getContentPane().repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //游戏胜利后直接返回不在执行键盘事件
        if (victory()) {
            return;
        }
        //左37 上38 右39 下40
        int code = e.getKeyCode();

        if (code == 37) {
            //x,y 与 x，y-1交换
            if (y == 0) {
                return;
            }

            data[x][y] = data[x][y - 1];
            data[x][y - 1] = 0;
            y--;
            step++;
            initImage();
        } else if (code == 38) {
            if (x == 0) {
                return;
            }
            //x,y与x-1,y交换
            data[x][y] = data[x - 1][y];
            data[x - 1][y] = 0;
            x--;
            step++;
            initImage();
        } else if (code == 39) {
            if (y == 3) {
                return;
            }
            //x,y与x,y+1交换
            data[x][y] = data[x][y + 1];
            data[x][y + 1] = 0;
            y++;
            step++;
            initImage();
        } else if (code == 40) {
            if (x == 3) {
                return;
            }
            //x,y与x+1,y交换
            data[x][y] = data[x + 1][y];
            data[x + 1][y] = 0;
            x++;
            step++;
            initImage();
        } else if (code == 65) {
            //按a键查看原图
            initImage();
        } else if (code == 87) {
            //按w键作弊一键通关
            for (int i = 0; i < 15; i++) {
                data[i / 4][i % 4] = i + 1;
            }
            data[3][3] = 0;
            initImage();
        }
    }

    public boolean victory() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != win[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == replayItem) {
            step = 0;
            initData();
            initImage();

        } else if (e.getSource() == reLoginItem) {
            //关闭当前界面
            this.setVisible(false);
            //打开登陆界面
            new LoginJframe();
        } else if (e.getSource() == closeItem) {
            //关闭虚拟机
            System.exit(0);
        } else if (e.getSource() == accountItem) {
            //创建一个弹窗对象
            JDialog jDialog = new JDialog();
            //创建一个管理图片的容器对象
            JLabel jLabel = new JLabel(new ImageIcon("puzzlegame/image/about.png"));
            //设置位置和宽高
            jLabel.setBounds(0, 0, 258, 258);
            //把图片添加到弹框中
            jDialog.getContentPane().add(jLabel);
            //给弹窗设置大小
            jDialog.setSize(344, 344);
            //让弹窗置顶
            jDialog.setAlwaysOnTop(true);
            //让弹窗居中
            jDialog.setLocationRelativeTo(null);
            //弹窗不关闭则无法操作下面的界面
            jDialog.setModal(true);
            //让弹窗显示出来
            jDialog.setVisible(true);

        } else if (e.getSource() == girlItem) {
            Random r = new Random();
            int index = r.nextInt(13)+1;
            path = "puzzlegame/image/girl/girl"+index;
            step = 0;
            initData();
            initImage();
        }else if (e.getSource() == animalItem) {
            Random r = new Random();
            int index = r.nextInt(8)+1;
            path = "puzzlegame/image/animal/animal"+index;
            step = 0;
            initData();
            initImage();
        }else if (e.getSource() == sportsItem) {
            Random r = new Random();
            int index = r.nextInt(8)+1;
            path = "puzzlegame/image/sport/sport"+index;
            step = 0;
            initData();
            initImage();
        }
    }
}
