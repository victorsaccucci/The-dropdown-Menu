/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package victor.menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author victo
 */
public class Menu extends JComponent{
    
    private MigLayout layout;
    private String [][] menuItems = new String[][] {
		{"Menu"}, 
		{"Coleção", " foto", "nome","data"},
                 {"Conta", " email", "login","data"},
                 {"Galeria"},
                 {"Sobre"}
                    
	};
    
    public Menu() {
		init();
	}

   private void init() {
		layout = new MigLayout("wrap 1, fillx, gapy 0, inset 2", "fill");
		setLayout(layout);
		setOpaque(true);
		for(int i = 0; i < menuItems.length; i++) {
			addMenu(menuItems[i][0],i);
		}
	}
   
   //exibit icones
   private Icon getIcon(int index){
       URL url = getClass().getResource("/victor/menu/" + index + ".png");
       if(url != null){
           return new ImageIcon(url);
       }else{
           return null;
       }
   }

   private void addMenu(String menuName, int index) {
		int length = menuItems[index].length;
		MenuItem item = new MenuItem(menuName, index, length>1);
                
                //exibit icones
                Icon icon = getIcon(index);
               
                if(icon != null){
                    item.setIcon(icon);
                }
                
                item.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent ae){
                        if(length > 1){
                            if(!item.isSelected()){
                                item.setSelected(true);
                                addSubMenu(item, index, length, getComponentZOrder(item));
                            }else{
                                hideMenu(item, index);
                                item.setSelected(false);
                            }
                            
                        }               
                    }       
                });            
		add(item);
		revalidate();
		repaint();
	}
   
   private void addSubMenu(MenuItem item, int index, int length, int indexZorder){
       JPanel panel = new JPanel(new MigLayout("Wrap 1, fillx, inset 0, gapy 0", "fill"));
       panel.setName(index + "");
       panel.setOpaque(false);
       
       for(int i = 1; i < length; i++){
           MenuItem subItem = new MenuItem(menuItems[index][i], i, false);
           subItem.initSubMenu(i, length);
           panel.add(subItem);
       }
       add(panel, "h 0!" ,indexZorder + 1);
       revalidate();
       repaint();
       MenuAnimation.showMenu(panel, item, layout, true);
   }
   
    private void hideMenu(MenuItem item, int index) {
        for(Component com : getComponents())  {
            if(com instanceof JPanel && com.getName() != null && com.getName().equals(index + "")){
                com.setName(null);
                MenuAnimation.showMenu(com, item, layout, false);
                break;
            }
        }                
    }
    @Override 
    protected void paintComponent(Graphics graphics){
        Graphics2D g2 = (Graphics2D)graphics.create();
        g2.setColor(new Color(21, 110, 70));
        g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
       super.paintComponent(graphics);
    }
    
}
