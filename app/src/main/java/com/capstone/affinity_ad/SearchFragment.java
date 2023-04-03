package com.capstone.affinity_ad;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.graphics.ImageDecoder;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment{
    //androidx.appcompat.widget.SearchView search_view;
    SettingList settingList;
    private List<List> list;          // 데이터를 넣은 리스트변수
    Context context ;
    private  List<String> l_brand;
    private  List<String> l_name;
    private  List<String> l_price;
    private  List<String> l_image;

    private  List<String>l_id;

    private ListView listView;          // 검색을 보여줄 리스트변수

    private GridView gridView;
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private SearchAdapter adapter;      // 리스트뷰에 연결할 아답터
    private ArrayList<String> arraylistbrand;
    private ArrayList<String> arraylistname;
    private ArrayList<String> arraylistprice;
    private ArrayList<String> arraylistimage;

    private ArrayList<String> arraylistid;
   private ArrayList<String> arraylist;

    private androidx.appcompat.widget.SearchView searchView;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View frag_view = inflater.inflate(R.layout.fragment_search,container,false);
        //editSearch = (EditText) frag_view.findViewById(R.id.editSearch);
        //listView = (ListView) frag_view.findViewById(R.id.listView);
        gridView = (GridView) frag_view.findViewById(R.id.gridView);
        searchView=frag_view.findViewById(R.id.search);
        context=container.getContext();

// 리스트를 생성한다.
        list = new ArrayList<List>();
        l_brand = new ArrayList<String>();
        l_name = new ArrayList<String>();
        l_price = new ArrayList<String>();
        l_image = new ArrayList<String>();
        l_id=new ArrayList<String>();
        // 검색에 사용할 데이터을 미리 저장한다.


        settingList();


        // 리스트의 모든 데이터를 arraylist에 복사한다.// list 복사본을 만든다.
        //arraylist = new ArrayList<String>();
        arraylistname = new ArrayList<String>();
        arraylistbrand = new ArrayList<String>();
        arraylistprice = new ArrayList<String>();
        arraylistimage = new ArrayList<String>();
        arraylistid=new ArrayList<String>();
        arraylistname.addAll(l_name);
        arraylistbrand.addAll(l_brand);
        arraylistprice.addAll(l_price);
        arraylistimage.addAll(l_image);
        arraylistid.addAll(l_id);
        //arraylist.addAll(l_name);

        // 리스트에 연동될 아답터를 생성한다.
        adapter = new SearchAdapter(l_brand,l_name,l_price,l_image,container.getContext() );
        // 리스트뷰에 아답터를 연결한다.
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "여기야 : "+l_id.get(position));
                Intent intent =new Intent(getActivity(),WebViewActivity.class);
                intent.putExtra("id",l_id.get(position));
                startActivity(intent);
            }
        });

//        editSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//            @Override
//            public void afterTextChanged(Editable editable) {
//                // input창에 문자를 입력할때마다 호출된다.
//                // search 메소드를 호출한다.
//                String text = editSearch.getText().toString();
//                search(text);
//            }
//        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                Log.d(TAG,"rrrrrrrrrrrrrrrrrrrrrr");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                search(newText);
                return false;
            }
        });



        return frag_view;

    }
    // 검색을 수행하는 메소드
    public void search(String charText) {
//        list = settingList.getlist();
//        l_brand.addAll(list.get(0));
//        l_name.addAll(list.get(1));
//        l_price.addAll(list.get(2));
//        l_image.addAll(list.get(3));
//        arraylistname.addAll(l_name);
//        arraylistbrand.addAll(l_brand);
//        arraylistprice.addAll(l_price);
//        arraylistimage.addAll(l_image);
        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        l_id.clear();
        l_name.clear();
        l_brand.clear();
        l_price.clear();
        l_image.clear();
        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            l_id.addAll(arraylistid);
            l_name.addAll(arraylistname);
            l_brand.addAll(arraylistbrand);
            l_price.addAll(arraylistprice);
            l_image.addAll(arraylistimage);
        }
        // 문자 입력을 할때..
        else
        {
            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < arraylistname.size(); i++)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (arraylistname.get(i).toLowerCase().contains(charText))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    l_id.add(arraylistid.get(i));
                    l_name.add(arraylistname.get(i));
                    l_brand.add(arraylistbrand.get(i));
                    l_price.add(arraylistprice.get(i));
                    l_image.add(arraylistimage.get(i));
                }
                else if(arraylistbrand.get(i).toLowerCase().contains(charText)){
                    l_id.add(arraylistid.get(i));
                    l_name.add(arraylistname.get(i));
                    l_brand.add(arraylistbrand.get(i));
                    l_price.add(arraylistprice.get(i));
                    l_image.add(arraylistimage.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }
    // 검색에 사용될 데이터를 리스트에 추가한다.
    private void settingList(){
        //settingList = new SettingList(context);


        //list = settingList.getlist();
//        l_brand.addAll(list.get(0));
//        l_name.addAll(list.get(1));
//        l_price.addAll(list.get(2));
//        l_image.addAll(list.get(3));
        l_id.addAll(settingList.l_id);
        l_brand.addAll(settingList.l_brand);
        l_name.addAll(settingList.l_name);
        l_price.addAll(settingList.l_price);
        l_image.addAll(settingList.l_image);

    }
}