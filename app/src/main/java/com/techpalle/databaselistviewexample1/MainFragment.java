package com.techpalle.databaselistviewexample1;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    EditText editTextName, editTextSubject;
    Button buttonSubmit;
    ListView listView;
    Cursor cursor;
    SimpleCursorAdapter simpleCursorAdapter;
    MyDataBase myDataBase;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDataBase = new MyDataBase(getActivity());
        myDataBase.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        editTextName = (EditText) v.findViewById(R.id.edit_text_name);
        editTextSubject = (EditText) v.findViewById(R.id.edit_text_subject);
        buttonSubmit = (Button) v.findViewById(R.id.button_submit);
        listView = (ListView) v.findViewById(R.id.list_view);
//CODE FOR DISPLAYING DATABASE TABLE IN LIST VIEW
        //A.
        cursor = myDataBase.queryStudent();
        //B. establish link between cursor and cursor adapter
        simpleCursorAdapter  = new SimpleCursorAdapter(getActivity(),R.layout.row, cursor,
                new String[]{"_id", "name", "subject"},
                new int[]{R.id.view_no, R.id.view_name, R.id.view_subjct});
        //C. establish link between Cursor Adapter to ListView
        listView.setAdapter(simpleCursorAdapter);

//HANDLING LIST VIEW ITEM CLICK
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //REQUEST CURSOR TO MOVE THE POSITION i
                cursor.moveToPosition(i);
                int sno = cursor.getInt(0);
                String name = cursor.getString(1);
                String subject = cursor.getString(2);
                Toast.makeText(getActivity(), sno+" "+name+" "+subject, Toast.LENGTH_SHORT).show();
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String subject = editTextSubject.getText().toString().trim();
                myDataBase.insertValue(name, subject);
                cursor.requery();
                Toast.makeText(getActivity(), "Value Added", Toast.LENGTH_SHORT).show();
                editTextName.setText("");
                editTextSubject.setText("");
                editTextName.requestFocus();
            }
        });
        return v;
    }

    @Override
    public void onDestroy() {
        myDataBase.close();
        super.onDestroy();
    }
}
