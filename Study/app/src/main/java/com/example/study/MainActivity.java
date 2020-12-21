package com.example.study;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    String mTitle[] = {"Вывод от 0 до 9","Вывод и объявление одномерного массива","Вывод strings одномерного массива ","Вывод strings в двумерном массиве","List<strings>","Сумма массива","Максимальный элемент массива","Минимальный элемент массива","Четные элементы массива","Нечетный элементы массива","Поиск по элементу","Бинарный поиск по индексу массива"};
    String nDescription[] = {
            "for(int i=0;i<10;i++){\n" +
                    "            System.out.println(i);\n" +
                    "      }\nРезультат: 0,1,2,3,4,5,6,7,8,9 ",
            "int arr[] = {5,4,3,7};\n" +
                    "        for (int i = 0; i < arr.length; i++) {\n" +
                    "            System.out.println(arr[i]);\n" +
                    "        } \nРезультат: 5,4,3,7      \n" + "",
            " String arrayStr[]={\"Hello\",\"World2\"};\n" +
                    "       for(String a:arrayStr){\n" +
                    "           System.out.println(a);\n" +
                    "       }\nРезультат: Hello,World",
            "String array[][] = {{\"Hello\"}, {\"String\"}};\n" +
                    "        for (int i = 0; i < array.length; i++) {\n" +
                    "            for (int j = 0; j < array[i].length; j++) {\n" +
                    "                System.out.println(array[i][j]);\n" +
                    "            }\n" +
                    "        }\nРезультат: Hello,String",
            "  List<String> items=new ArrayList<>();\n" +
                    "        items.add(\"Chamber\");\n" +
                    "        items.add(\"Garry\");\n" +
                    "        items.add(\"Potter\");\n" +
                    "        items.forEach(item->System.out.println(item));\nРезультат: Chamber,Garry,Potter",
            "    int totalArray = 0;\n" +
                    "        int array[] = {5, 4, 3, 2, 7, 9, 11, 105};\n" +
                    "        for (int i = 0; i < array.length; i++) {\n" +
                    "            totalArray += array[i];\n" +
                    "        }\n" +
                    "        System.out.println(\"Сумма \"+totalArray);\nРезультат:146",

            "   int totalArray = Integer.MIN_VALUE;\n" +
                    "        int array[] = {5, 4, 3, 2, 147, 9, 11, 105};\n" +
                    "        for (int i = 0; i < array.length; i++) {\n" +
                    "            totalArray = Math.max(totalArray, array3[i]);\n" +
                    "        }\n" +
                    "           System.out.println(totalArray3);\nРезультат:147",
            "  int totalArray = Integer.MAX_VALUE;\n" +
                    "        int array[] = {5, 4, 3, 2, 147, 9, 11, 105};\n" +
                    "        for (int i = 0; i < array.length; i++) {\n" +
                    "            totalArray = Math.min(totalArray, array[i]);\n" +
                    "        }\n" +
                    "                System.out.println(totalArray);\nРезультат:2",

            " double[] myList = {22, 3,25.7 , 12, 23.8};\n" +
                    "for (int i = 0; i < myList.length; i++) {\n" +
                    "            if (myList[i] % 2 == 0) {\n" +
                    "                  Arrays.sort(myList);//Сортировка массива\n" +
                    "                  System.out.print(myList[i]+\", \");  }\nРезультат: четные элементы массива myList: 3.0, 12.0, 22.0 }",

            " double[] myList = {22, 3,25.7 , 12, 23.8};\n" +
                    "  for (int i = 0; i < myList.length; i++) {\n" +
                    "            if (myList[i] % 2 != 0) {\n" +
                    "                System.out.print(myList[i] + \", \");\n" +
                    "            }\n" +
                    "        }\nРезультат: нечетные элементы массива myList: 2.5, 1.8, 1.3, 6.5, 22.8",

            " int[] anArray = new int[]{5, 2, 1, 4, 8};\n" +
                    "        for (int i = 0; i < anArray.length; i++) {\n" +
                    "            if (anArray[i] == 4) {\n" +
                    "                System.out.println(\"Found at index \" + i);\n" +
                    "                break;\n" +
                    "            }\n" +
                    "        }\nРезультат найден по индексу:(3)",
            "int[]anArray = new int[]{1, 2, 3, 4, 5};\n" +
                    "        int index = Arrays.binarySearch(anArray, 4);\n" +
                    "        System.out.println(\"Found at index \" + index);\n" +
                    "        System.out.print(\"\\n\");\n" +
                    "    }\nРезультат найден по индексу:(3)"};
    TextView titleText;
    TextView descriptionText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        MyAdapter myadapter = new MyAdapter(this, mTitle, nDescription);
        listView.setAdapter(myadapter);
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if(position==0){
                   Toast.makeText(MainActivity.this,"Ya",Toast.LENGTH_SHORT);
               }
           }
       });
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String rTitle[];
        String rDescription[];

        MyAdapter(Context c, String title[], String description[]) {
            super(c, R.layout.raw, R.id.titleId, title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.raw, parent, false);
            titleText = row.findViewById(R.id.titleId);
            descriptionText = row.findViewById(R.id.descriptionId);

            titleText.setText(rTitle[position]);
            descriptionText.setText((rDescription[position]));

            return row;
        }
    }

}