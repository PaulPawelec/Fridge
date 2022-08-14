package com.example.fridge;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Diet_View extends AppCompatActivity {
    String name, category, desc, rec1, rec2, rec3, rec4, rec5, rec6, rec7, rec8, rec9, rec10, rec11, rec12, rec13, rec14, rec15, rec16, rec17, rec18, rec19, rec20, rec21;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getRec1() {
        return rec1;
    }
    public void setRec1(String rec1) {
        this.rec1 = rec1;
    }
    public String getRec2() {
        return rec2;
    }
    public void setRec2(String rec2) {
        this.rec2 = rec2;
    }
    public String getRec3() {
        return rec3;
    }
    public void setRec3(String rec3) {
        this.rec3 = rec3;
    }
    public String getRec4() {
        return rec4;
    }
    public void setRec4(String rec4) {
        this.rec4 = rec4;
    }
    public String getRec5() {
        return rec5;
    }
    public void setRec5(String rec5) {
        this.rec5 = rec5;
    }
    public String getRec6() {
        return rec6;
    }
    public void setRec6(String rec6) {
        this.rec6 = rec6;
    }
    public String getRec7() {
        return rec7;
    }
    public void setRec7(String rec7) {
        this.rec7 = rec7;
    }
    public String getRec8() {
        return rec8;
    }
    public void setRec8(String rec8) {
        this.rec8 = rec8;
    }
    public String getRec9() {
        return rec9;
    }
    public void setRec9(String rec9) {
        this.rec9 = rec9;
    }
    public String getRec10() {
        return rec10;
    }
    public void setRec10(String rec10) {
        this.rec10 = rec10;
    }
    public String getRec11() {
        return rec11;
    }
    public void setRec11(String rec11) {
        this.rec11 = rec11;
    }
    public String getRec12() {
        return rec12;
    }
    public void setRec12(String rec12) {
        this.rec12 = rec12;
    }
    public String getRec13() {
        return rec13;
    }
    public void setRec13(String rec13) {
        this.rec13 = rec13;
    }
    public String getRec14() {
        return rec14;
    }
    public void setRec14(String rec14) {
        this.rec14 = rec14;
    }
    public String getRec15() {
        return rec15;
    }
    public void setRec15(String rec15) {
        this.rec15 = rec15;
    }
    public String getRec16() {
        return rec16;
    }
    public void setRec16(String rec16) {
        this.rec16 = rec16;
    }
    public String getRec17() {
        return rec17;
    }
    public void setRec17(String rec17) {
        this.rec17 = rec17;
    }
    public String getRec18() {
        return rec18;
    }
    public void setRec18(String rec18) {
        this.rec18 = rec18;
    }
    public String getRec19() {
        return rec19;
    }
    public void setRec19(String rec19) {
        this.rec19 = rec19;
    }
    public String getRec20() {
        return rec20;
    }
    public void setRec20(String rec20) {
        this.rec20 = rec20;
    }
    public String getRec21() {
        return rec21;
    }
    public void setRec21(String rec21) {
        this.rec21 = rec21;
    }

    //String [] recipes ={"Pancakes", "Scrambled Eggs", "Roast Chicken", "Spaghetti Carbonara", "French Fries"};
    //String [] recipesid ={"1", "2", "69", "4", "5"};
    ArrayList<String> recipesnames = new ArrayList<>();
    ArrayList<String> recipesid = new ArrayList<>();
    Recipe diet;
    ArrayAdapter<String> adaptercat;
    TextView recipe1, recipe2, recipe3, recipe4, recipe5, recipe6, recipe7, recipe8, recipe9, recipe10, recipe11, recipe12, recipe13, recipe14, recipe15, recipe16, recipe17, recipe18, recipe19, recipe20, recipe21;
    Spinner recipeSpinner1, recipeSpinner2, recipeSpinner3, recipeSpinner4, recipeSpinner5, recipeSpinner6, recipeSpinner7, recipeSpinner8, recipeSpinner9, recipeSpinner10, recipeSpinner11, recipeSpinner12, recipeSpinner13, recipeSpinner14, recipeSpinner15, recipeSpinner16, recipeSpinner17, recipeSpinner18, recipeSpinner19, recipeSpinner20, recipeSpinner21;
    Button view1, edit1, ok1, view2, edit2, ok2, view3, edit3, ok3, view4, edit4, ok4, view5, edit5, ok5, view6, edit6, ok6, view7, edit7, ok7, view8, edit8, ok8, view9, edit9, ok9, view10, edit10, ok10, view11, edit11, ok11, view12, edit12, ok12, view13, edit13, ok13, view14, edit14, ok14, view15, edit15, ok15, view16, edit16, ok16, view17, edit17, ok17, view18, edit18, ok18, view19, edit19, ok19, view20, edit20, ok20, view21, edit21, ok21;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diet_view);

        TextView dietsViewLabel = findViewById(R.id.dietsLabel);
        TextView diet_category = findViewById(R.id.diet_category);
        TextView diet_desc = findViewById(R.id.diet_desc);

        Intent intent = getIntent();
        diet = (Recipe) intent.getSerializableExtra("name");

        String messageToServer = "0603\n"+diet.getIDrecipe()+"\nq";
        System.out.println("Message:"+messageToServer);

        Diet_View.MessageSender messageSender = new Diet_View.MessageSender();
        try {
            messageSender.execute(messageToServer).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        messageToServer = "0509\n"+"recipes"+"\nq"; ////////////////RECIPES
        System.out.println("Message:"+messageToServer);
        messageSender = new Diet_View.MessageSender();
        try {
            messageSender.execute(messageToServer).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dietsViewLabel.setText(diet.getName());
        diet_category.setText(getCategory());
        diet_desc.setText(getDesc());

        recipeSpinner1 = (Spinner) findViewById(R.id.recipeSpinner1);
        adaptercat = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, recipesnames);
        recipeSpinner1.setAdapter(adaptercat);

        recipe1 = findViewById(R.id.recipe1);
        recipe1.setText(getRec1());
        view1 = findViewById(R.id.show1);
        edit1 = findViewById(R.id.edit1);
        ok1 = findViewById(R.id.ok1);

        recipeSpinner2 = (Spinner) findViewById(R.id.recipeSpinner2);
        recipeSpinner2.setAdapter(adaptercat);

        recipe2 = findViewById(R.id.recipe2);
        recipe2.setText(getRec2());
        view2 = findViewById(R.id.show2);
        edit2 = findViewById(R.id.edit2);
        ok2 = findViewById(R.id.ok2);

        recipeSpinner3 = (Spinner) findViewById(R.id.recipeSpinner3);
        recipeSpinner3.setAdapter(adaptercat);

        recipe3 = findViewById(R.id.recipe3);
        recipe3.setText(getRec3());
        view3 = findViewById(R.id.show3);
        edit3 = findViewById(R.id.edit3);
        ok3 = findViewById(R.id.ok3);

        recipeSpinner4 = (Spinner) findViewById(R.id.recipeSpinner4);
        recipeSpinner4.setAdapter(adaptercat);

        recipe4 = findViewById(R.id.recipe4);
        recipe4.setText(getRec4());
        view4 = findViewById(R.id.show4);
        edit4 = findViewById(R.id.edit4);
        ok4 = findViewById(R.id.ok4);

        recipeSpinner5 = (Spinner) findViewById(R.id.recipeSpinner5);
        recipeSpinner5.setAdapter(adaptercat);

        recipe5 = findViewById(R.id.recipe5);
        recipe5.setText(getRec5());
        view5 = findViewById(R.id.show5);
        edit5 = findViewById(R.id.edit5);
        ok5 = findViewById(R.id.ok5);

        recipeSpinner6 = (Spinner) findViewById(R.id.recipeSpinner6);
        recipeSpinner6.setAdapter(adaptercat);

        recipe6 = findViewById(R.id.recipe6);
        recipe6.setText(getRec6());
        view6 = findViewById(R.id.show6);
        edit6 = findViewById(R.id.edit6);
        ok6 = findViewById(R.id.ok6);

        recipeSpinner7 = (Spinner) findViewById(R.id.recipeSpinner7);
        recipeSpinner7.setAdapter(adaptercat);

        recipe7 = findViewById(R.id.recipe7);
        recipe7.setText(getRec7());
        view7 = findViewById(R.id.show7);
        edit7 = findViewById(R.id.edit7);
        ok7 = findViewById(R.id.ok7);

        recipeSpinner8 = (Spinner) findViewById(R.id.recipeSpinner8);
        recipeSpinner8.setAdapter(adaptercat);

        recipe8 = findViewById(R.id.recipe8);
        recipe8.setText(getRec8());
        view8 = findViewById(R.id.show8);
        edit8 = findViewById(R.id.edit8);
        ok8 = findViewById(R.id.ok8);

        recipeSpinner9 = (Spinner) findViewById(R.id.recipeSpinner9);
        recipeSpinner9.setAdapter(adaptercat);

        recipe9 = findViewById(R.id.recipe9);
        recipe9.setText(getRec9());
        view9 = findViewById(R.id.show9);
        edit9 = findViewById(R.id.edit9);
        ok9 = findViewById(R.id.ok9);

        recipeSpinner10 = (Spinner) findViewById(R.id.recipeSpinner10);
        recipeSpinner10.setAdapter(adaptercat);

        recipe10 = findViewById(R.id.recipe10);
        recipe10.setText(getRec10());
        view10 = findViewById(R.id.show10);
        edit10 = findViewById(R.id.edit10);
        ok10 = findViewById(R.id.ok10);

        recipeSpinner11 = (Spinner) findViewById(R.id.recipeSpinner11);
        recipeSpinner11.setAdapter(adaptercat);

        recipe11 = findViewById(R.id.recipe11);
        recipe11.setText(getRec11());
        view11 = findViewById(R.id.show11);
        edit11 = findViewById(R.id.edit11);
        ok11 = findViewById(R.id.ok11);

        recipeSpinner12 = (Spinner) findViewById(R.id.recipeSpinner12);
        recipeSpinner12.setAdapter(adaptercat);

        recipe12 = findViewById(R.id.recipe12);
        recipe12.setText(getRec12());
        view12 = findViewById(R.id.show12);
        edit12 = findViewById(R.id.edit12);
        ok12 = findViewById(R.id.ok12);

        recipeSpinner13 = (Spinner) findViewById(R.id.recipeSpinner13);
        recipeSpinner13.setAdapter(adaptercat);

        recipe13 = findViewById(R.id.recipe13);
        recipe13.setText(getRec13());
        view13 = findViewById(R.id.show13);
        edit13 = findViewById(R.id.edit13);
        ok13 = findViewById(R.id.ok13);

        recipeSpinner14 = (Spinner) findViewById(R.id.recipeSpinner14);
        recipeSpinner14.setAdapter(adaptercat);

        recipe14 = findViewById(R.id.recipe14);
        recipe14.setText(getRec14());
        view14 = findViewById(R.id.show14);
        edit14 = findViewById(R.id.edit14);
        ok14 = findViewById(R.id.ok14);

        recipeSpinner15 = (Spinner) findViewById(R.id.recipeSpinner15);
        recipeSpinner15.setAdapter(adaptercat);

        recipe15 = findViewById(R.id.recipe15);
        recipe15.setText(getRec15());
        view15 = findViewById(R.id.show15);
        edit15 = findViewById(R.id.edit15);
        ok15 = findViewById(R.id.ok15);

        recipeSpinner16 = (Spinner) findViewById(R.id.recipeSpinner16);
        recipeSpinner16.setAdapter(adaptercat);

        recipe16 = findViewById(R.id.recipe16);
        recipe16.setText(getRec16());
        view16 = findViewById(R.id.show16);
        edit16 = findViewById(R.id.edit16);
        ok16 = findViewById(R.id.ok16);

        recipeSpinner17 = (Spinner) findViewById(R.id.recipeSpinner17);
        recipeSpinner17.setAdapter(adaptercat);

        recipe17 = findViewById(R.id.recipe17);
        recipe17.setText(getRec17());
        view17 = findViewById(R.id.show17);
        edit17 = findViewById(R.id.edit17);
        ok17 = findViewById(R.id.ok17);

        recipeSpinner18 = (Spinner) findViewById(R.id.recipeSpinner18);
        recipeSpinner18.setAdapter(adaptercat);

        recipe18 = findViewById(R.id.recipe18);
        recipe18.setText(getRec18());
        view18 = findViewById(R.id.show18);
        edit18 = findViewById(R.id.edit18);
        ok18 = findViewById(R.id.ok18);

        recipeSpinner19 = (Spinner) findViewById(R.id.recipeSpinner19);
        recipeSpinner19.setAdapter(adaptercat);

        recipe19 = findViewById(R.id.recipe19);
        recipe19.setText(getRec19());
        view19 = findViewById(R.id.show19);
        edit19 = findViewById(R.id.edit19);
        ok19 = findViewById(R.id.ok19);

        recipeSpinner20 = (Spinner) findViewById(R.id.recipeSpinner20);
        recipeSpinner20.setAdapter(adaptercat);

        recipe20 = findViewById(R.id.recipe20);
        recipe20.setText(getRec20());
        view20 = findViewById(R.id.show20);
        edit20 = findViewById(R.id.edit20);
        ok20 = findViewById(R.id.ok20);

        recipeSpinner21 = (Spinner) findViewById(R.id.recipeSpinner21);
        recipeSpinner21.setAdapter(adaptercat);

        recipe21 = findViewById(R.id.recipe21);
        recipe21.setText(getRec21());
        view21 = findViewById(R.id.show21);
        edit21 = findViewById(R.id.edit21);
        ok21 = findViewById(R.id.ok21);

        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View1();
            }
        });
        edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit1();
            }
        });
        ok1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ok1();
                String messageToServer = "0602\n"+diet.getIDrecipe()+"\n"+"1"+"\n"+recipesid.get(recipesnames.indexOf(recipe1.getText().toString())).toString()+"\nq";
                System.out.println(messageToServer);

                Diet_View.MessageSender messageSender = new Diet_View.MessageSender();
                try {
                    messageSender.execute(messageToServer).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View2();
            }
        });
        edit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit2();
            }
        });
        ok2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ok2();
                String messageToServer = "0602\n"+diet.getIDrecipe()+"\n"+"2"+"\n"+recipesid.get(recipesnames.indexOf(recipe2.getText().toString())).toString()+"\nq";
                System.out.println(messageToServer);

                Diet_View.MessageSender messageSender = new Diet_View.MessageSender();
                try {
                    messageSender.execute(messageToServer).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View3();
            }
        });
        edit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit3();
            }
        });
        ok3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ok3();
                String messageToServer = "0602\n"+diet.getIDrecipe()+"\n"+"3"+"\n"+recipesid.get(recipesnames.indexOf(recipe3.getText().toString())).toString()+"\nq";
                System.out.println(messageToServer);

                Diet_View.MessageSender messageSender = new Diet_View.MessageSender();
                try {
                    messageSender.execute(messageToServer).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View4();
            }
        });
        edit4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit4();
            }
        });
        ok4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ok4();
                String messageToServer = "0602\n"+diet.getIDrecipe()+"\n"+"4"+"\n"+recipesid.get(recipesnames.indexOf(recipe4.getText().toString())).toString()+"\nq";
                System.out.println(messageToServer);

                Diet_View.MessageSender messageSender = new Diet_View.MessageSender();
                try {
                    messageSender.execute(messageToServer).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        view5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View5();
            }
        });
        edit5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit5();
            }
        });
        ok5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ok5();
                String messageToServer = "0602\n"+diet.getIDrecipe()+"\n"+"5"+"\n"+recipesid.get(recipesnames.indexOf(recipe5.getText().toString())).toString()+"\nq";
                System.out.println(messageToServer);

                Diet_View.MessageSender messageSender = new Diet_View.MessageSender();
                try {
                    messageSender.execute(messageToServer).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        view6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View6();
            }
        });
        edit6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit6();
            }
        });
        ok6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ok6();
                String messageToServer = "0602\n"+diet.getIDrecipe()+"\n"+"6"+"\n"+recipesid.get(recipesnames.indexOf(recipe6.getText().toString())).toString()+"\nq";
                System.out.println(messageToServer);

                Diet_View.MessageSender messageSender = new Diet_View.MessageSender();
                try {
                    messageSender.execute(messageToServer).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        view7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View7();
            }
        });
        edit7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit7();
            }
        });
        ok7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ok7();
                String messageToServer = "0602\n"+diet.getIDrecipe()+"\n"+"7"+"\n"+recipesid.get(recipesnames.indexOf(recipe7.getText().toString())).toString()+"\nq";
                System.out.println(messageToServer);

                Diet_View.MessageSender messageSender = new Diet_View.MessageSender();
                try {
                    messageSender.execute(messageToServer).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        view8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View8();
            }
        });
        edit8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit8();
            }
        });
        ok8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ok8();
                String messageToServer = "0602\n"+diet.getIDrecipe()+"\n"+"8"+"\n"+recipesid.get(recipesnames.indexOf(recipe8.getText().toString())).toString()+"\nq";
                System.out.println(messageToServer);

                Diet_View.MessageSender messageSender = new Diet_View.MessageSender();
                try {
                    messageSender.execute(messageToServer).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        view9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View9();
            }
        });
        edit9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit9();
            }
        });
        ok9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ok9();
                String messageToServer = "0602\n"+diet.getIDrecipe()+"\n"+"9"+"\n"+recipesid.get(recipesnames.indexOf(recipe9.getText().toString())).toString()+"\nq";
                System.out.println(messageToServer);

                Diet_View.MessageSender messageSender = new Diet_View.MessageSender();
                try {
                    messageSender.execute(messageToServer).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        view10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View10();
            }
        });
        edit10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit10();
            }
        });
        ok10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ok10();
                String messageToServer = "0602\n"+diet.getIDrecipe()+"\n"+"10"+"\n"+recipesid.get(recipesnames.indexOf(recipe10.getText().toString())).toString()+"\nq";
                System.out.println(messageToServer);

                Diet_View.MessageSender messageSender = new Diet_View.MessageSender();
                try {
                    messageSender.execute(messageToServer).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        view11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View11();
            }
        });
        edit11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit11();
            }
        });
        ok11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ok11();
                String messageToServer = "0602\n"+diet.getIDrecipe()+"\n"+"11"+"\n"+recipesid.get(recipesnames.indexOf(recipe11.getText().toString())).toString()+"\nq";
                System.out.println(messageToServer);

                Diet_View.MessageSender messageSender = new Diet_View.MessageSender();
                try {
                    messageSender.execute(messageToServer).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        view12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View12();
            }
        });
        edit12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit12();
            }
        });
        ok12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ok12();
                String messageToServer = "0602\n"+diet.getIDrecipe()+"\n"+"12"+"\n"+recipesid.get(recipesnames.indexOf(recipe12.getText().toString())).toString()+"\nq";
                System.out.println(messageToServer);

                Diet_View.MessageSender messageSender = new Diet_View.MessageSender();
                try {
                    messageSender.execute(messageToServer).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        view13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View13();
            }
        });
        edit13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit13();
            }
        });
        ok13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ok13();
                String messageToServer = "0602\n"+diet.getIDrecipe()+"\n"+"13"+"\n"+recipesid.get(recipesnames.indexOf(recipe13.getText().toString())).toString()+"\nq";
                System.out.println(messageToServer);

                Diet_View.MessageSender messageSender = new Diet_View.MessageSender();
                try {
                    messageSender.execute(messageToServer).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        view14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View14();
            }
        });
        edit14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit14();
            }
        });
        ok14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ok14();
                String messageToServer = "0602\n"+diet.getIDrecipe()+"\n"+"14"+"\n"+recipesid.get(recipesnames.indexOf(recipe14.getText().toString())).toString()+"\nq";
                System.out.println(messageToServer);

                Diet_View.MessageSender messageSender = new Diet_View.MessageSender();
                try {
                    messageSender.execute(messageToServer).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        view15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View15();
            }
        });
        edit15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit15();
            }
        });
        ok15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ok15();
                String messageToServer = "0602\n"+diet.getIDrecipe()+"\n"+"15"+"\n"+recipesid.get(recipesnames.indexOf(recipe15.getText().toString())).toString()+"\nq";
                System.out.println(messageToServer);

                Diet_View.MessageSender messageSender = new Diet_View.MessageSender();
                try {
                    messageSender.execute(messageToServer).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        view16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View16();
            }
        });
        edit16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit16();
            }
        });
        ok16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ok16();
                String messageToServer = "0602\n"+diet.getIDrecipe()+"\n"+"16"+"\n"+recipesid.get(recipesnames.indexOf(recipe16.getText().toString())).toString()+"\nq";
                System.out.println(messageToServer);

                Diet_View.MessageSender messageSender = new Diet_View.MessageSender();
                try {
                    messageSender.execute(messageToServer).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        view17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View17();
            }
        });
        edit17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit17();
            }
        });
        ok17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ok17();
                String messageToServer = "0602\n"+diet.getIDrecipe()+"\n"+"17"+"\n"+recipesid.get(recipesnames.indexOf(recipe17.getText().toString())).toString()+"\nq";
                System.out.println(messageToServer);

                Diet_View.MessageSender messageSender = new Diet_View.MessageSender();
                try {
                    messageSender.execute(messageToServer).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        view18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View18();
            }
        });
        edit18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit18();
            }
        });
        ok18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ok18();
                String messageToServer = "0602\n"+diet.getIDrecipe()+"\n"+"18"+"\n"+recipesid.get(recipesnames.indexOf(recipe18.getText().toString())).toString()+"\nq";
                System.out.println(messageToServer);

                Diet_View.MessageSender messageSender = new Diet_View.MessageSender();
                try {
                    messageSender.execute(messageToServer).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        view19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View19();
            }
        });
        edit19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit19();
            }
        });
        ok19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ok19();
                String messageToServer = "0602\n"+diet.getIDrecipe()+"\n"+"19"+"\n"+recipesid.get(recipesnames.indexOf(recipe19.getText().toString())).toString()+"\nq";
                System.out.println(messageToServer);

                Diet_View.MessageSender messageSender = new Diet_View.MessageSender();
                try {
                    messageSender.execute(messageToServer).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        view20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View20();
            }
        });
        edit20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit20();
            }
        });
        ok20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ok20();
                String messageToServer = "0602\n"+diet.getIDrecipe()+"\n"+"20"+"\n"+recipesid.get(recipesnames.indexOf(recipe20.getText().toString())).toString()+"\nq";
                System.out.println(messageToServer);

                Diet_View.MessageSender messageSender = new Diet_View.MessageSender();
                try {
                    messageSender.execute(messageToServer).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        view21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View21();
            }
        });
        edit21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit21();
            }
        });
        ok21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ok21();
                String messageToServer = "0602\n"+diet.getIDrecipe()+"\n"+"21"+"\n"+recipesid.get(recipesnames.indexOf(recipe21.getText().toString())).toString()+"\nq";
                System.out.println(messageToServer);

                Diet_View.MessageSender messageSender = new Diet_View.MessageSender();
                try {
                    messageSender.execute(messageToServer).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    public void View1(){
        Recipe recipeTest = new Recipe();
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner1.getAdapter();

        recipeTest.setName(recipe1.getText().toString());
        int x = Integer.parseInt(recipesid.get(recipesnames.indexOf(recipe1.getText().toString())));
        recipeTest.setIDrecipe(x);
        startActivity(new Intent(Diet_View.this, SuggestMeal_View.class).putExtra("name", recipeTest));
    }
    public void Edit1(){
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner1.getAdapter();
        recipe1.setVisibility(View.INVISIBLE);
        view1.setVisibility(View.INVISIBLE);
        edit1.setVisibility(View.INVISIBLE);
        ok1.setVisibility(View.VISIBLE);
        recipeSpinner1.setVisibility(View.VISIBLE);
        recipeSpinner1.setSelection(adapter.getPosition(recipe1.getText()));
    }
    public void Ok1(){
        recipe1.setVisibility(View.VISIBLE);
        view1.setVisibility(View.VISIBLE);
        edit1.setVisibility(View.VISIBLE);
        ok1.setVisibility(View.INVISIBLE);
        recipeSpinner1.setVisibility(View.INVISIBLE);
        recipe1.setText(recipeSpinner1.getSelectedItem().toString());
    }
    public void View2(){
        Recipe recipeTest = new Recipe();
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner2.getAdapter();

        recipeTest.setName(recipe2.getText().toString());
        //recipeTest.setIDrecipe(adapter.getPosition(recipe2.getText())+1);
        int x = Integer.parseInt(recipesid.get(recipesnames.indexOf(recipe2.getText().toString())));
        recipeTest.setIDrecipe(x);

        startActivity(new Intent(Diet_View.this, SuggestMeal_View.class).putExtra("name", recipeTest));
    }
    public void Edit2(){
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner2.getAdapter();
        recipe2.setVisibility(View.INVISIBLE);
        view2.setVisibility(View.INVISIBLE);
        edit2.setVisibility(View.INVISIBLE);
        ok2.setVisibility(View.VISIBLE);
        recipeSpinner2.setVisibility(View.VISIBLE);
        recipeSpinner2.setSelection(adapter.getPosition(recipe2.getText()));
    }
    public void Ok2(){
        recipe2.setVisibility(View.VISIBLE);
        view2.setVisibility(View.VISIBLE);
        edit2.setVisibility(View.VISIBLE);
        ok2.setVisibility(View.INVISIBLE);
        recipeSpinner2.setVisibility(View.INVISIBLE);
        recipe2.setText(recipeSpinner2.getSelectedItem().toString());
    }
    public void View3(){
        Recipe recipeTest = new Recipe();
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner3.getAdapter();

        recipeTest.setName(recipe3.getText().toString());
        //recipeTest.setIDrecipe(adapter.getPosition(recipe3.getText())+1);

        int x = Integer.parseInt(recipesid.get(recipesnames.indexOf(recipe3.getText().toString())));
        recipeTest.setIDrecipe(x);

        startActivity(new Intent(Diet_View.this, SuggestMeal_View.class).putExtra("name", recipeTest));
    }
    public void Edit3(){
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner3.getAdapter();
        recipe3.setVisibility(View.INVISIBLE);
        view3.setVisibility(View.INVISIBLE);
        edit3.setVisibility(View.INVISIBLE);
        ok3.setVisibility(View.VISIBLE);
        recipeSpinner3.setVisibility(View.VISIBLE);
        recipeSpinner3.setSelection(adapter.getPosition(recipe3.getText()));
    }
    public void Ok3(){
        recipe3.setVisibility(View.VISIBLE);
        view3.setVisibility(View.VISIBLE);
        edit3.setVisibility(View.VISIBLE);
        ok3.setVisibility(View.INVISIBLE);
        recipeSpinner3.setVisibility(View.INVISIBLE);
        recipe3.setText(recipeSpinner3.getSelectedItem().toString());
    }
    public void View4(){
        Recipe recipeTest = new Recipe();
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner4.getAdapter();

        recipeTest.setName(recipe4.getText().toString());
        //recipeTest.setIDrecipe(adapter.getPosition(recipe4.getText())+1);

        int x = Integer.parseInt(recipesid.get(recipesnames.indexOf(recipe4.getText().toString())));
        recipeTest.setIDrecipe(x);

        startActivity(new Intent(Diet_View.this, SuggestMeal_View.class).putExtra("name", recipeTest));
    }
    public void Edit4(){
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner4.getAdapter();
        recipe4.setVisibility(View.INVISIBLE);
        view4.setVisibility(View.INVISIBLE);
        edit4.setVisibility(View.INVISIBLE);
        ok4.setVisibility(View.VISIBLE);
        recipeSpinner4.setVisibility(View.VISIBLE);
        recipeSpinner4.setSelection(adapter.getPosition(recipe4.getText()));
    }
    public void Ok4(){
        recipe4.setVisibility(View.VISIBLE);
        view4.setVisibility(View.VISIBLE);
        edit4.setVisibility(View.VISIBLE);
        ok4.setVisibility(View.INVISIBLE);
        recipeSpinner4.setVisibility(View.INVISIBLE);
        recipe4.setText(recipeSpinner4.getSelectedItem().toString());
    }
    public void View5(){
        Recipe recipeTest = new Recipe();
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner5.getAdapter();

        recipeTest.setName(recipe5.getText().toString());
        //recipeTest.setIDrecipe(adapter.getPosition(recipe5.getText())+1);

        int x = Integer.parseInt(recipesid.get(recipesnames.indexOf(recipe5.getText().toString())));
        recipeTest.setIDrecipe(x);

        startActivity(new Intent(Diet_View.this, SuggestMeal_View.class).putExtra("name", recipeTest));
    }
    public void Edit5(){
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner5.getAdapter();
        recipe5.setVisibility(View.INVISIBLE);
        view5.setVisibility(View.INVISIBLE);
        edit5.setVisibility(View.INVISIBLE);
        ok5.setVisibility(View.VISIBLE);
        recipeSpinner5.setVisibility(View.VISIBLE);
        recipeSpinner5.setSelection(adapter.getPosition(recipe5.getText()));
    }
    public void Ok5(){
        recipe5.setVisibility(View.VISIBLE);
        view5.setVisibility(View.VISIBLE);
        edit5.setVisibility(View.VISIBLE);
        ok5.setVisibility(View.INVISIBLE);
        recipeSpinner5.setVisibility(View.INVISIBLE);
        recipe5.setText(recipeSpinner5.getSelectedItem().toString());
    }
    public void View6(){
        Recipe recipeTest = new Recipe();
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner6.getAdapter();

        recipeTest.setName(recipe6.getText().toString());

        int x = Integer.parseInt(recipesid.get(recipesnames.indexOf(recipe6.getText().toString())));
        recipeTest.setIDrecipe(x);

        startActivity(new Intent(Diet_View.this, SuggestMeal_View.class).putExtra("name", recipeTest));
    }
    public void Edit6(){
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner6.getAdapter();
        recipe6.setVisibility(View.INVISIBLE);
        view6.setVisibility(View.INVISIBLE);
        edit6.setVisibility(View.INVISIBLE);
        ok6.setVisibility(View.VISIBLE);
        recipeSpinner6.setVisibility(View.VISIBLE);
        recipeSpinner6.setSelection(adapter.getPosition(recipe6.getText()));
    }
    public void Ok6(){
        recipe6.setVisibility(View.VISIBLE);
        view6.setVisibility(View.VISIBLE);
        edit6.setVisibility(View.VISIBLE);
        ok6.setVisibility(View.INVISIBLE);
        recipeSpinner6.setVisibility(View.INVISIBLE);
        recipe6.setText(recipeSpinner6.getSelectedItem().toString());
    }
    public void View7(){
        Recipe recipeTest = new Recipe();
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner7.getAdapter();

        recipeTest.setName(recipe7.getText().toString());

        int x = Integer.parseInt(recipesid.get(recipesnames.indexOf(recipe7.getText().toString())));
        recipeTest.setIDrecipe(x);

        startActivity(new Intent(Diet_View.this, SuggestMeal_View.class).putExtra("name", recipeTest));
    }
    public void Edit7(){
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner7.getAdapter();
        recipe7.setVisibility(View.INVISIBLE);
        view7.setVisibility(View.INVISIBLE);
        edit7.setVisibility(View.INVISIBLE);
        ok7.setVisibility(View.VISIBLE);
        recipeSpinner7.setVisibility(View.VISIBLE);
        recipeSpinner7.setSelection(adapter.getPosition(recipe7.getText()));
    }
    public void Ok7(){
        recipe7.setVisibility(View.VISIBLE);
        view7.setVisibility(View.VISIBLE);
        edit7.setVisibility(View.VISIBLE);
        ok7.setVisibility(View.INVISIBLE);
        recipeSpinner7.setVisibility(View.INVISIBLE);
        recipe7.setText(recipeSpinner7.getSelectedItem().toString());
    }
    public void View8(){
        Recipe recipeTest = new Recipe();
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner8.getAdapter();

        recipeTest.setName(recipe8.getText().toString());

        int x = Integer.parseInt(recipesid.get(recipesnames.indexOf(recipe8.getText().toString())));
        recipeTest.setIDrecipe(x);

        startActivity(new Intent(Diet_View.this, SuggestMeal_View.class).putExtra("name", recipeTest));
    }
    public void Edit8(){
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner8.getAdapter();
        recipe8.setVisibility(View.INVISIBLE);
        view8.setVisibility(View.INVISIBLE);
        edit8.setVisibility(View.INVISIBLE);
        ok8.setVisibility(View.VISIBLE);
        recipeSpinner8.setVisibility(View.VISIBLE);
        recipeSpinner8.setSelection(adapter.getPosition(recipe8.getText()));
    }
    public void Ok8(){
        recipe8.setVisibility(View.VISIBLE);
        view8.setVisibility(View.VISIBLE);
        edit8.setVisibility(View.VISIBLE);
        ok8.setVisibility(View.INVISIBLE);
        recipeSpinner8.setVisibility(View.INVISIBLE);
        recipe8.setText(recipeSpinner8.getSelectedItem().toString());
    }
    public void View9(){
        Recipe recipeTest = new Recipe();
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner9.getAdapter();

        recipeTest.setName(recipe9.getText().toString());
        int x = Integer.parseInt(recipesid.get(recipesnames.indexOf(recipe9.getText().toString())));
        recipeTest.setIDrecipe(x);
        startActivity(new Intent(Diet_View.this, SuggestMeal_View.class).putExtra("name", recipeTest));
    }
    public void Edit9(){
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner9.getAdapter();
        recipe9.setVisibility(View.INVISIBLE);
        view9.setVisibility(View.INVISIBLE);
        edit9.setVisibility(View.INVISIBLE);
        ok9.setVisibility(View.VISIBLE);
        recipeSpinner9.setVisibility(View.VISIBLE);
        recipeSpinner9.setSelection(adapter.getPosition(recipe9.getText()));
    }
    public void Ok9(){
        recipe9.setVisibility(View.VISIBLE);
        view9.setVisibility(View.VISIBLE);
        edit9.setVisibility(View.VISIBLE);
        ok9.setVisibility(View.INVISIBLE);
        recipeSpinner9.setVisibility(View.INVISIBLE);
        recipe9.setText(recipeSpinner9.getSelectedItem().toString());
    }
    public void View10(){
        Recipe recipeTest = new Recipe();
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner10.getAdapter();

        recipeTest.setName(recipe10.getText().toString());
        int x = Integer.parseInt(recipesid.get(recipesnames.indexOf(recipe10.getText().toString())));
        recipeTest.setIDrecipe(x);
        startActivity(new Intent(Diet_View.this, SuggestMeal_View.class).putExtra("name", recipeTest));
    }
    public void Edit10(){
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner10.getAdapter();
        recipe10.setVisibility(View.INVISIBLE);
        view10.setVisibility(View.INVISIBLE);
        edit10.setVisibility(View.INVISIBLE);
        ok10.setVisibility(View.VISIBLE);
        recipeSpinner10.setVisibility(View.VISIBLE);
        recipeSpinner10.setSelection(adapter.getPosition(recipe10.getText()));
    }
    public void Ok10(){
        recipe10.setVisibility(View.VISIBLE);
        view10.setVisibility(View.VISIBLE);
        edit10.setVisibility(View.VISIBLE);
        ok10.setVisibility(View.INVISIBLE);
        recipeSpinner10.setVisibility(View.INVISIBLE);
        recipe10.setText(recipeSpinner10.getSelectedItem().toString());
    }
    public void View11(){
        Recipe recipeTest = new Recipe();
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner11.getAdapter();

        recipeTest.setName(recipe11.getText().toString());
        int x = Integer.parseInt(recipesid.get(recipesnames.indexOf(recipe11.getText().toString())));
        recipeTest.setIDrecipe(x);
        startActivity(new Intent(Diet_View.this, SuggestMeal_View.class).putExtra("name", recipeTest));
    }
    public void Edit11(){
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner11.getAdapter();
        recipe11.setVisibility(View.INVISIBLE);
        view11.setVisibility(View.INVISIBLE);
        edit11.setVisibility(View.INVISIBLE);
        ok11.setVisibility(View.VISIBLE);
        recipeSpinner11.setVisibility(View.VISIBLE);
        recipeSpinner11.setSelection(adapter.getPosition(recipe11.getText()));
    }
    public void Ok11(){
        recipe11.setVisibility(View.VISIBLE);
        view11.setVisibility(View.VISIBLE);
        edit11.setVisibility(View.VISIBLE);
        ok11.setVisibility(View.INVISIBLE);
        recipeSpinner11.setVisibility(View.INVISIBLE);
        recipe11.setText(recipeSpinner11.getSelectedItem().toString());
    }
    public void View12(){
        Recipe recipeTest = new Recipe();
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner12.getAdapter();

        recipeTest.setName(recipe12.getText().toString());
        int x = Integer.parseInt(recipesid.get(recipesnames.indexOf(recipe12.getText().toString())));
        recipeTest.setIDrecipe(x);
        startActivity(new Intent(Diet_View.this, SuggestMeal_View.class).putExtra("name", recipeTest));
    }
    public void Edit12(){
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner12.getAdapter();
        recipe12.setVisibility(View.INVISIBLE);
        view12.setVisibility(View.INVISIBLE);
        edit12.setVisibility(View.INVISIBLE);
        ok12.setVisibility(View.VISIBLE);
        recipeSpinner12.setVisibility(View.VISIBLE);
        recipeSpinner12.setSelection(adapter.getPosition(recipe12.getText()));
    }
    public void Ok12(){
        recipe12.setVisibility(View.VISIBLE);
        view12.setVisibility(View.VISIBLE);
        edit12.setVisibility(View.VISIBLE);
        ok12.setVisibility(View.INVISIBLE);
        recipeSpinner12.setVisibility(View.INVISIBLE);
        recipe12.setText(recipeSpinner12.getSelectedItem().toString());
    }
    public void View13(){
        Recipe recipeTest = new Recipe();
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner13.getAdapter();

        recipeTest.setName(recipe13.getText().toString());
        int x = Integer.parseInt(recipesid.get(recipesnames.indexOf(recipe13.getText().toString())));
        recipeTest.setIDrecipe(x);
        startActivity(new Intent(Diet_View.this, SuggestMeal_View.class).putExtra("name", recipeTest));
    }
    public void Edit13(){
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner13.getAdapter();
        recipe13.setVisibility(View.INVISIBLE);
        view13.setVisibility(View.INVISIBLE);
        edit13.setVisibility(View.INVISIBLE);
        ok13.setVisibility(View.VISIBLE);
        recipeSpinner13.setVisibility(View.VISIBLE);
        recipeSpinner13.setSelection(adapter.getPosition(recipe13.getText()));
    }
    public void Ok13(){
        recipe13.setVisibility(View.VISIBLE);
        view13.setVisibility(View.VISIBLE);
        edit13.setVisibility(View.VISIBLE);
        ok13.setVisibility(View.INVISIBLE);
        recipeSpinner13.setVisibility(View.INVISIBLE);
        recipe13.setText(recipeSpinner13.getSelectedItem().toString());
    }
    public void View14(){
        Recipe recipeTest = new Recipe();
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner14.getAdapter();

        recipeTest.setName(recipe14.getText().toString());
        int x = Integer.parseInt(recipesid.get(recipesnames.indexOf(recipe14.getText().toString())));
        recipeTest.setIDrecipe(x);
        startActivity(new Intent(Diet_View.this, SuggestMeal_View.class).putExtra("name", recipeTest));
    }
    public void Edit14(){
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner14.getAdapter();
        recipe14.setVisibility(View.INVISIBLE);
        view14.setVisibility(View.INVISIBLE);
        edit14.setVisibility(View.INVISIBLE);
        ok14.setVisibility(View.VISIBLE);
        recipeSpinner14.setVisibility(View.VISIBLE);
        recipeSpinner14.setSelection(adapter.getPosition(recipe14.getText()));
    }
    public void Ok14(){
        recipe14.setVisibility(View.VISIBLE);
        view14.setVisibility(View.VISIBLE);
        edit14.setVisibility(View.VISIBLE);
        ok14.setVisibility(View.INVISIBLE);
        recipeSpinner14.setVisibility(View.INVISIBLE);
        recipe14.setText(recipeSpinner14.getSelectedItem().toString());
    }
    public void View15(){
        Recipe recipeTest = new Recipe();
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner15.getAdapter();

        recipeTest.setName(recipe15.getText().toString());
        int x = Integer.parseInt(recipesid.get(recipesnames.indexOf(recipe15.getText().toString())));
        recipeTest.setIDrecipe(x);
        startActivity(new Intent(Diet_View.this, SuggestMeal_View.class).putExtra("name", recipeTest));
    }
    public void Edit15(){
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner15.getAdapter();
        recipe15.setVisibility(View.INVISIBLE);
        view15.setVisibility(View.INVISIBLE);
        edit15.setVisibility(View.INVISIBLE);
        ok15.setVisibility(View.VISIBLE);
        recipeSpinner15.setVisibility(View.VISIBLE);
        recipeSpinner15.setSelection(adapter.getPosition(recipe15.getText()));
    }
    public void Ok15(){
        recipe15.setVisibility(View.VISIBLE);
        view15.setVisibility(View.VISIBLE);
        edit15.setVisibility(View.VISIBLE);
        ok15.setVisibility(View.INVISIBLE);
        recipeSpinner15.setVisibility(View.INVISIBLE);
        recipe15.setText(recipeSpinner15.getSelectedItem().toString());
    }
    public void View16(){
        Recipe recipeTest = new Recipe();
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner16.getAdapter();

        recipeTest.setName(recipe16.getText().toString());
        int x = Integer.parseInt(recipesid.get(recipesnames.indexOf(recipe16.getText().toString())));
        recipeTest.setIDrecipe(x);
        startActivity(new Intent(Diet_View.this, SuggestMeal_View.class).putExtra("name", recipeTest));
    }
    public void Edit16(){
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner16.getAdapter();
        recipe16.setVisibility(View.INVISIBLE);
        view16.setVisibility(View.INVISIBLE);
        edit16.setVisibility(View.INVISIBLE);
        ok16.setVisibility(View.VISIBLE);
        recipeSpinner16.setVisibility(View.VISIBLE);
        recipeSpinner16.setSelection(adapter.getPosition(recipe16.getText()));
    }
    public void Ok16(){
        recipe16.setVisibility(View.VISIBLE);
        view16.setVisibility(View.VISIBLE);
        edit16.setVisibility(View.VISIBLE);
        ok16.setVisibility(View.INVISIBLE);
        recipeSpinner16.setVisibility(View.INVISIBLE);
        recipe16.setText(recipeSpinner16.getSelectedItem().toString());
    }
    public void View17(){
        Recipe recipeTest = new Recipe();
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner17.getAdapter();

        recipeTest.setName(recipe17.getText().toString());
        int x = Integer.parseInt(recipesid.get(recipesnames.indexOf(recipe17.getText().toString())));
        recipeTest.setIDrecipe(x);
        startActivity(new Intent(Diet_View.this, SuggestMeal_View.class).putExtra("name", recipeTest));
    }
    public void Edit17(){
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner17.getAdapter();
        recipe17.setVisibility(View.INVISIBLE);
        view17.setVisibility(View.INVISIBLE);
        edit17.setVisibility(View.INVISIBLE);
        ok17.setVisibility(View.VISIBLE);
        recipeSpinner17.setVisibility(View.VISIBLE);
        recipeSpinner17.setSelection(adapter.getPosition(recipe17.getText()));
    }
    public void Ok17(){
        recipe17.setVisibility(View.VISIBLE);
        view17.setVisibility(View.VISIBLE);
        edit17.setVisibility(View.VISIBLE);
        ok17.setVisibility(View.INVISIBLE);
        recipeSpinner17.setVisibility(View.INVISIBLE);
        recipe17.setText(recipeSpinner17.getSelectedItem().toString());
    }
    public void View18(){
        Recipe recipeTest = new Recipe();
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner18.getAdapter();

        recipeTest.setName(recipe18.getText().toString());
        int x = Integer.parseInt(recipesid.get(recipesnames.indexOf(recipe18.getText().toString())));
        recipeTest.setIDrecipe(x);
        startActivity(new Intent(Diet_View.this, SuggestMeal_View.class).putExtra("name", recipeTest));
    }
    public void Edit18(){
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner18.getAdapter();
        recipe18.setVisibility(View.INVISIBLE);
        view18.setVisibility(View.INVISIBLE);
        edit18.setVisibility(View.INVISIBLE);
        ok18.setVisibility(View.VISIBLE);
        recipeSpinner18.setVisibility(View.VISIBLE);
        recipeSpinner18.setSelection(adapter.getPosition(recipe18.getText()));
    }
    public void Ok18(){
        recipe18.setVisibility(View.VISIBLE);
        view18.setVisibility(View.VISIBLE);
        edit18.setVisibility(View.VISIBLE);
        ok18.setVisibility(View.INVISIBLE);
        recipeSpinner18.setVisibility(View.INVISIBLE);
        recipe18.setText(recipeSpinner18.getSelectedItem().toString());
    }
    public void View19(){
        Recipe recipeTest = new Recipe();
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner19.getAdapter();

        recipeTest.setName(recipe19.getText().toString());
        int x = Integer.parseInt(recipesid.get(recipesnames.indexOf(recipe19.getText().toString())));
        recipeTest.setIDrecipe(x);
        startActivity(new Intent(Diet_View.this, SuggestMeal_View.class).putExtra("name", recipeTest));
    }
    public void Edit19(){
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner19.getAdapter();
        recipe19.setVisibility(View.INVISIBLE);
        view19.setVisibility(View.INVISIBLE);
        edit19.setVisibility(View.INVISIBLE);
        ok19.setVisibility(View.VISIBLE);
        recipeSpinner19.setVisibility(View.VISIBLE);
        recipeSpinner19.setSelection(adapter.getPosition(recipe19.getText()));
    }
    public void Ok19(){
        recipe19.setVisibility(View.VISIBLE);
        view19.setVisibility(View.VISIBLE);
        edit19.setVisibility(View.VISIBLE);
        ok19.setVisibility(View.INVISIBLE);
        recipeSpinner19.setVisibility(View.INVISIBLE);
        recipe19.setText(recipeSpinner19.getSelectedItem().toString());
    }
    public void View20(){
        Recipe recipeTest = new Recipe();
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner20.getAdapter();

        recipeTest.setName(recipe20.getText().toString());
        int x = Integer.parseInt(recipesid.get(recipesnames.indexOf(recipe20.getText().toString())));
        recipeTest.setIDrecipe(x);
        startActivity(new Intent(Diet_View.this, SuggestMeal_View.class).putExtra("name", recipeTest));
    }
    public void Edit20(){
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner20.getAdapter();
        recipe20.setVisibility(View.INVISIBLE);
        view20.setVisibility(View.INVISIBLE);
        edit20.setVisibility(View.INVISIBLE);
        ok20.setVisibility(View.VISIBLE);
        recipeSpinner20.setVisibility(View.VISIBLE);
        recipeSpinner20.setSelection(adapter.getPosition(recipe20.getText()));
    }
    public void Ok20(){
        recipe20.setVisibility(View.VISIBLE);
        view20.setVisibility(View.VISIBLE);
        edit20.setVisibility(View.VISIBLE);
        ok20.setVisibility(View.INVISIBLE);
        recipeSpinner20.setVisibility(View.INVISIBLE);
        recipe20.setText(recipeSpinner20.getSelectedItem().toString());
    }
    public void View21(){
        Recipe recipeTest = new Recipe();
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner21.getAdapter();

        recipeTest.setName(recipe21.getText().toString());
        int x = Integer.parseInt(recipesid.get(recipesnames.indexOf(recipe21.getText().toString())));
        recipeTest.setIDrecipe(x);
        startActivity(new Intent(Diet_View.this, SuggestMeal_View.class).putExtra("name", recipeTest));
    }
    public void Edit21(){
        ArrayAdapter adapter = (ArrayAdapter) recipeSpinner21.getAdapter();
        recipe21.setVisibility(View.INVISIBLE);
        view21.setVisibility(View.INVISIBLE);
        edit21.setVisibility(View.INVISIBLE);
        ok21.setVisibility(View.VISIBLE);
        recipeSpinner21.setVisibility(View.VISIBLE);
        recipeSpinner21.setSelection(adapter.getPosition(recipe21.getText()));
    }
    public void Ok21(){
        recipe21.setVisibility(View.VISIBLE);
        view21.setVisibility(View.VISIBLE);
        edit21.setVisibility(View.VISIBLE);
        ok21.setVisibility(View.INVISIBLE);
        recipeSpinner21.setVisibility(View.INVISIBLE);
        recipe21.setText(recipeSpinner21.getSelectedItem().toString());
    }

    class MessageSender extends AsyncTask<String,Void,Void> {
        public boolean correctConnection;
        Socket socket;
        DataOutputStream output;
        PrintWriter writer;

        @Override
        protected Void doInBackground(String... voids) {
            System.out.println("Do in background & create server connection");
            Diet_View.Odbjur odbjur = new Diet_View.Odbjur();
            correctConnection = odbjur.serverRequest(voids[0]);

            return null;
        }
    }

    class Odbjur{
        public boolean serverRequest(String request) {
            int port;
            port = 50080;
            String hostname = "192.168.1.2";

            try (Socket socket = new Socket(hostname, port)) {

                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                if(!LoginSessions.getInstance().session.equals("")){
                    writer.println("session"+LoginSessions.getInstance().session);
                }else{
                    writer.println("nosession");
                }

                writer.println(request);
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String serverMessage = reader.readLine();

                if(serverMessage.equals("null")) System.out.println("Connection with the server lost");
                else{
                    System.out.println(serverMessage);
                    if(serverMessage.startsWith("-1"))
                        System.out.println("Correct connection & wrong data");
                    else if(serverMessage.startsWith("0509")){
                        int n = Integer.parseInt(reader.readLine());
                        for (int i = 0; i < n; i++) {
                            String id = reader.readLine();
                            String name = reader.readLine();
                            String category = reader.readLine();

                            recipesid.add(id);
                            recipesnames.add(name);
                        }
                    }
                    else if(serverMessage.startsWith("0602")){
                        System.out.println("Correct 0602");
                    }
                    else if(serverMessage.startsWith("0603")){
                        System.out.println("Correct connection");
                        String name = reader.readLine();
                        String category = reader.readLine();
                        String desc = reader.readLine();

                        String rec1 = reader.readLine();
                        String rec2 = reader.readLine();
                        String rec3 = reader.readLine();
                        String rec4 = reader.readLine();
                        String rec5 = reader.readLine();
                        String rec6 = reader.readLine();
                        String rec7 = reader.readLine();
                        String rec8 = reader.readLine();
                        String rec9 = reader.readLine();
                        String rec10 = reader.readLine();
                        String rec11 = reader.readLine();
                        String rec12 = reader.readLine();
                        String rec13 = reader.readLine();
                        String rec14 = reader.readLine();
                        String rec15 = reader.readLine();
                        String rec16 = reader.readLine();
                        String rec17 = reader.readLine();
                        String rec18 = reader.readLine();
                        String rec19 = reader.readLine();
                        String rec20 = reader.readLine();
                        String rec21 = reader.readLine();

                        setName(name);
                        setCategory(category);
                        setDesc(desc);
                        setRec1(rec1);
                        setRec2(rec2);
                        setRec3(rec3);
                        setRec4(rec4);
                        setRec5(rec5);
                        setRec6(rec6);
                        setRec7(rec7);
                        setRec8(rec8);
                        setRec9(rec9);
                        setRec10(rec10);
                        setRec11(rec11);
                        setRec12(rec12);
                        setRec13(rec13);
                        setRec14(rec14);
                        setRec15(rec15);
                        setRec16(rec16);
                        setRec17(rec17);
                        setRec18(rec18);
                        setRec19(rec19);
                        setRec20(rec20);
                        setRec21(rec21);
                    }
                }

            } catch (UnknownHostException ex) {
                System.out.println("Server not found: " + ex.getMessage());
            } catch (IOException ex) {
                System.out.println("I/O error: " + ex.getMessage());
            }
            return false;
        }
    }
}
