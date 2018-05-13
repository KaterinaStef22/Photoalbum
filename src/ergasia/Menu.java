package ergasia;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;



public class Menu  {
	public static JFrame mainframe;
	public static JPanel loginpanel ;
	public static JPanel userpanel ;
	public static Boolean Logged = false;
	public static String ConnectedUser;
	public static JButton photoupload;
	public static JPanel albumpanel;
	public static JScrollPane scralbumpanel;
	public static File rootpath;
	public static String Level;
	
	
	public Menu() {
		
	}
	
	public void CreateMenu() {
		CreateMainFrame();
		if (!Logged) {
			CreateLoginMenu();	
			
		}else {
			CreateLoggedMenu();
			CreateAlbumPanel();
			mainframe.remove(loginpanel);
			mainframe.revalidate();
			mainframe.repaint();
			
		}
	
	}
	
	
	public static void SetRootPath() {  
		try {
			rootpath = new File(Photo.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()+ConnectedUser);
		} catch (URISyntaxException e) {
			
			e.printStackTrace();
		}
	}

	public static void CreateMainFrame() {
		Dimension othoni = Toolkit.getDefaultToolkit().getScreenSize(); 
    	mainframe = new JFrame("Photo Album");
    	mainframe.setSize(1400, 850); 
    	mainframe.setLocation((othoni.width-mainframe.getSize().width)/2,(othoni.height-mainframe.getSize().height)/2);  
		mainframe.setResizable(false); 
		JLabel background =new JLabel(new ImageIcon("background.png"));
		background.setSize(mainframe.getSize().width,mainframe.getSize().height);
		mainframe.add(background);
    	mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    	mainframe.setLayout(null);
    	mainframe.setVisible(true); 
	}



	
	public static void CreateLoginMenu() {
		loginpanel = new JPanel(); 
	    loginpanel.setBackground(new Color(0,0,0,125));
	    loginpanel.setSize(500, 250); 
	    loginpanel.setLocation((mainframe.getSize().width-loginpanel.getSize().width)/2,(mainframe.getSize().height-loginpanel.getSize().height)/2); 
	    loginpanel.setBorder(BorderFactory.createLineBorder(Color.black)); 
	    JLabel title = new JLabel("Welcome guest. Please Log in...",SwingConstants.CENTER); 
	    title.setFont(new Font("Arial", Font.BOLD, 20)); 
	    title.setLocation(0, 20); 
	    title.setSize(500,50); 
	    title.setForeground(Color.WHITE);
	    JLabel labelusername = new JLabel("Username:"); 
	    labelusername.setFont(new Font("Arial", Font.BOLD, 12)); 
	    labelusername.setLocation(100, 80);
	    labelusername.setSize(100,50);
	    labelusername.setForeground(Color.WHITE);
	    JTextField textusername = new JTextField(); 
	    textusername.setFont(new Font("Arial", Font.BOLD, 12)); 
	    textusername.setLocation(190, 90);
	    textusername.setSize(200,30);
	    textusername.setText("");
	    JLabel labelpassword = new JLabel("Password"); 
	    labelpassword.setFont(new Font("Arial", Font.BOLD, 12));
	    labelpassword.setLocation(100, 130);
	    labelpassword.setSize(100,50);
	    labelpassword.setForeground(Color.WHITE);
	    JPasswordField textpassword = new JPasswordField(); 
	    textpassword.setFont(new Font("Arial", Font.BOLD, 12));
	    textpassword.setLocation(190, 140);
	    textpassword.setSize(200,30);
	    textpassword.setText("");
	    JButton buttonlogin = new JButton("Log In"); 
	    buttonlogin.setFont(new Font("Arial", Font.BOLD, 12)); 
	    buttonlogin.setSize(120,40); 
	    buttonlogin.setLocation(230, 190); 
	    buttonlogin.addActionListener(new ActionListener(){ 
	      public void actionPerformed(ActionEvent e) {
	    	  String GivenUsername = textusername.getText().toString();
	    	  String GivenPassword = String.valueOf(textpassword.getPassword());
	    	  
	    	if (Login(GivenUsername,GivenPassword) == false) { 
	    		JOptionPane.showMessageDialog(mainframe, "Wrong login credentials! Please try again"); 
	    	}else {
	    		Logged = true;
	    		ConnectedUser = GivenUsername;
	    		SetRootPath();
	    		JOptionPane.showMessageDialog(mainframe, "Hello back, " + ConnectedUser );
	    		CreateFolder();
	    		CreateLoggedMenu();
	    		CreateAlbumPanel();
	    		mainframe.remove(loginpanel);
	    	}
	      }
	    });
	    loginpanel.add(title);         
	    loginpanel.add(labelusername); 
	    loginpanel.add(textusername);  
	    loginpanel.add(labelpassword); 
	    loginpanel.add(textpassword); 
	    loginpanel.add(buttonlogin);  
	    loginpanel.setLayout(null);  
	    
	    mainframe.add(loginpanel,0); 
		mainframe.revalidate();
		mainframe.repaint();
	}

	private static void CreateLoggedMenu() { 
		userpanel = new JPanel();
		userpanel.setBackground(new Color(0,0,0,125));
		userpanel.setSize(90,90);
		userpanel.setLocation(mainframe.getSize().width-userpanel.getSize().width-10,10);
		userpanel.setBorder(BorderFactory.createLineBorder(Color.black));
		JLabel labelusername = new JLabel(ConnectedUser, SwingConstants.CENTER); 
		labelusername.setFont(new Font("Arial", Font.BOLD, 12)); 
		labelusername.setLocation(25,42);
		labelusername.setSize(40,20);
		labelusername.setForeground(Color.BLACK);
		JLabel labeluserphoto = new JLabel(ConnectedUser); 
	    labeluserphoto.setFont(new Font("Arial", Font.BOLD, 12)); 
	    labeluserphoto.setLocation(20,10);
	    labeluserphoto.setSize(50,50);
	    ImageIcon labelimage = new ImageIcon(new ImageIcon("user.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
	    labeluserphoto.setIcon(labelimage);
		JButton buttonlogout = new JButton("Log Out"); 
		buttonlogout.setFont(new Font("Arial", Font.BOLD, 12)); 
	    buttonlogout.setSize(80,20); 
	    buttonlogout.setLocation(5,65);
	    buttonlogout.addActionListener(new ActionListener(){ 
	      public void actionPerformed(ActionEvent e) {
			Logged=false;
			ConnectedUser="";
			mainframe.remove(userpanel);
			mainframe.remove(albumpanel);
			mainframe.remove(scralbumpanel);
			mainframe.revalidate();
			mainframe.repaint();
			CreateLoginMenu();	
			Level="";
	      
	      }
	    });
	    userpanel.add(labelusername);
	    userpanel.add(labeluserphoto);
	    userpanel.add(buttonlogout);
	    userpanel.setLayout(null);
	    mainframe.add(userpanel,1); 
		mainframe.revalidate();
		mainframe.repaint();
	}


	private static void CreateFolder() {
		new File(rootpath.toString()).mkdirs();
	}

	private static void CreateAlbumPanel() {
		albumpanel = new JPanel();
		albumpanel.setOpaque(false);
	    albumpanel.setSize(mainframe.getSize().width-120,30000); 
	    albumpanel.setLocation(10,10); 
	    albumpanel.setBorder(BorderFactory.createLineBorder(Color.black)); 
	    
	    if(Level == null || Level.isEmpty()) { 
	    	AddAlbumToPanel();
	    	albumpanel.add(CreateNewAlbumButton());

	    }else {
	    	AddImagesToPanel(Level);
	    	albumpanel.add(CreateNewImageUploadButton());
	    	albumpanel.add(CreateGoBackButton());
	    	
	    }
	    
	    albumpanel.setLayout(null);
	    scralbumpanel=new JScrollPane(albumpanel); 
	    scralbumpanel.setBounds(new Rectangle(10, 10,mainframe.getSize().width-120,mainframe.getSize().height-50));
	    scralbumpanel.setOpaque(false);
	    scralbumpanel.getViewport().setBackground(new Color(0,0,0,195));
	    mainframe.add(scralbumpanel,0); 
	    mainframe.revalidate();
	    mainframe.repaint();
	}


	
      
    

	private static JButton CreateNewImageUploadButton() {
	   photoupload = new JButton("Upload Photo");
	   photoupload.setFont(new Font("Arial", Font.BOLD, 12)); 
	   photoupload.setSize(180,40); 
	   photoupload.setLocation(10,10); 
       photoupload.addActionListener(new ActionListener(){ 
           public void actionPerformed(ActionEvent e) {   
        	   String SelectedPhoto = SelectFile().toString();
				Photo newphoto = new Photo(SelectedPhoto);
				try {
			
					if (newphoto.CopyPhoto(SelectedPhoto,ConnectedUser+"/"+Level)) {
						JOptionPane.showMessageDialog(null, "The photo was uploaded.", "Information", JOptionPane.INFORMATION_MESSAGE);
						mainframe.remove(scralbumpanel);
						CreateAlbumPanel();
					}
				} catch (IOException | URISyntaxException e1) {
					
					e1.printStackTrace();
				}
           }
       });
       return photoupload;
	}
	
	private static JButton CreateNewAlbumButton() {
		   photoupload = new JButton("Create New Album");
		   photoupload.setFont(new Font("Arial", Font.BOLD, 12)); 
		   photoupload.setSize(180,40); 
		   photoupload.setLocation(10,10); 
	       photoupload.addActionListener(new ActionListener(){ 
	           public void actionPerformed(ActionEvent e) {   
					String name = JOptionPane.showInputDialog("Name your new Album");
					    Calendar cal = Calendar.getInstance();
					    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
					    if(name!=null && name.length()>0) {
					        if (Files.exists(new File(rootpath + "/" + name).toPath())) {
					            name = name + " " +sdf.format(cal.getTime());
					            new File(rootpath + "/" + name).mkdirs();
					            System.out.println(new File(rootpath + "/" + name).toPath());
					        } else {
					            new File(rootpath + "/" + name).mkdirs();
					            System.out.println(new File(rootpath + "/" + name).toPath());
					        }
					        mainframe.remove(scralbumpanel);
							CreateAlbumPanel();
					    }
	           }
	       });
	       return photoupload;
		}

	 private static JButton CreateGoBackButton() {
	        photoupload = new JButton("Go Back");
	        photoupload.setFont(new Font("Arial", Font.BOLD, 12));
	        photoupload.setSize(180, 40);
	        photoupload.setLocation(250, 10);
	        photoupload.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	               Level="";
	               mainframe.remove(scralbumpanel);
					CreateAlbumPanel();
	            }
	        });
	        return photoupload;
	       
	 }


	private static void AddImagesToPanel(String lvl) {
	   JButton button;
	   File f = new File(rootpath.getPath()+"/"+lvl);
			if (f.exists()) {
				File[] listOfFiles = f.listFiles();
				  	int x = 10;
			        int y = 60;
			        System.out.println(listOfFiles.length);
				    for (int i = 0; i < listOfFiles.length; i++) {
				      if (listOfFiles[i].isFile()) {
				    	  Photo thisphoto = new Photo(f+"/"+listOfFiles[i].getName());
				    	  String k = f+"/"+listOfFiles[i].getName();
				    	  
				    	  button = new JButton(new ImageIcon(((new ImageIcon(thisphoto.photopath).getImage().getScaledInstance(140, 100,java.awt.Image.SCALE_SMOOTH)))));
				    	  button.setToolTipText("<html>Filename:"+thisphoto.name+ "<br>Photo Path:"+thisphoto.photopath+ "<br>Width:"+thisphoto.width + "<br>Height:" + thisphoto.height+"</html>");
				    	  button.setSize(140,100);
				    	  button.addMouseListener(new MouseAdapter(){
				                public void mouseClicked(MouseEvent e){
				                	JFrame frame = new JFrame();
				                	 int answer = JOptionPane.showConfirmDialog((Component) null, "Are u sure that u want delete this photo ?","Alert", JOptionPane.YES_NO_OPTION);
				                
				                    if (answer == JOptionPane.YES_OPTION) {
				                      File fw = new File(k);
				                     try {
										delete(fw);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
				                     mainframe.remove(scralbumpanel);
				     				CreateAlbumPanel();
				                    } else if (answer == JOptionPane.NO_OPTION) {
				                      // User clicked NO.
				                    }
				                }
				            });
							if (x+155<albumpanel.getSize().width) {
								button.setLocation(x,y); 
								x=x+155;
							}else {
								x=10;
								button.setLocation(x,y+115); 
								y=y+115;
							}
							albumpanel.setPreferredSize(new Dimension(mainframe.getSize().width-140, y+115));
							albumpanel.add(button);
							albumpanel.revalidate();
							
				      } else if (listOfFiles[i].isDirectory()) {
				        
				      }
				    }
			}
	}

	private static void AddAlbumToPanel() {
		File file = rootpath;
        String[] fileList = file.list();
    	int x = 10;
        int y = 60;
        ImageIcon imageIcon = new ImageIcon("folder.png");
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(110, 110,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back
        for(String name:fileList){
        	JButton FolderButton = new JButton(name,imageIcon);
        	FolderButton.setHorizontalTextPosition(JButton.CENTER);
        	FolderButton.setVerticalTextPosition(JButton.CENTER);
        	FolderButton.setSize(140, 100);
        	FolderButton.setOpaque(false);
        	FolderButton.setContentAreaFilled(false);
        	FolderButton.setBorderPainted(false);
        	FolderButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        	if (x+155<albumpanel.getSize().width) {
        		FolderButton.setLocation(x,y); 
				x=x+155;
			}else {
				x=10;
				FolderButton.setLocation(x,y+115); 
				y=y+115;
			}
        	FolderButton.addActionListener(new ActionListener(){ 
 	           public void actionPerformed(ActionEvent e) {   
 	        	   Level = FolderButton.getText();
 	        	  mainframe.remove(scralbumpanel);
				CreateAlbumPanel();
 	        	   
 	        	   
 	           }
        	});
        	FolderButton.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e){
                	JFrame frame = new JFrame();
                	 int answer = JOptionPane.showConfirmDialog((Component) null, "Are u sure that u want delete the album with title: "+FolderButton.getText(),"Alert", JOptionPane.YES_NO_OPTION);
                
                    if (answer == JOptionPane.YES_OPTION) {
                      File f = new File(rootpath+"/"+FolderButton.getText());
                     try {
						delete(f);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                     mainframe.remove(scralbumpanel);
     				CreateAlbumPanel();
                    } else if (answer == JOptionPane.NO_OPTION) {
                      // User clicked NO.
                    }
                }
            });
        	albumpanel.add(FolderButton);
			albumpanel.revalidate();
            System.out.println(name);
        }
	}

	 public static void delete(File file)
		    	throws IOException{
		 
		    	if(file.isDirectory()){
		 
		    		//directory is empty, then delete it
		    		if(file.list().length==0){
		    			
		    		   file.delete();
		    		   System.out.println("Directory is deleted : " 
		                                                 + file.getAbsolutePath());
		    			
		    		}else{
		    			
		    		   //list all the directory contents
		        	   String files[] = file.list();
		     
		        	   for (String temp : files) {
		        	      //construct the file structure
		        	      File fileDelete = new File(file, temp);
		        		 
		        	      //recursive delete
		        	     delete(fileDelete);
		        	   }
		        		
		        	   //check the directory again, if empty then delete it
		        	   if(file.list().length==0){
		           	     file.delete();
		        	     System.out.println("Directory is deleted : " 
		                                                  + file.getAbsolutePath());
		        	   }
		    		}
		    		
		    	}else{
		    		//if file, then delete it
		    		file.delete();
		    		System.out.println("File is deleted : " + file.getAbsolutePath());
		    	}
		    }


	private static Boolean Login(String Username, String Password) { 
    	JdbcCon con;
		try {
			con = new JdbcCon();
			if (!con.CheckUser(Username, Password)) {
				return false;
			}
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			
		}
    	return true;
    } 
    
    public static String SelectFile() {
    	String filename="";
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
        chooser.setFileFilter(imageFilter);
        if (chooser.showOpenDialog(mainframe) == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            filename = f.getAbsolutePath().toString();
            //GetMetaData(f);
        } else {
        }
        return filename;
    }   
    
    
    
   /* public static void GetMetaData(File path){
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(path);
            System.out.println(metadata.getDirectories());
            for (Directory directory : metadata.getDirectories()) {
                //
                // Each Directory stores values in Tag objects
                //
                for (Tag tag : directory.getTags()) {
                    System.out.println(tag);
                }

                //
                // Each Directory may also contain error messages
                //
                for (String error : directory.getErrors()) {
                    System.err.println("ERROR: " + error);
                }
            }
        } catch (ImageProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


}



