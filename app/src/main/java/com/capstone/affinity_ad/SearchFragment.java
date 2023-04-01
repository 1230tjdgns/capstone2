package com.capstone.affinity_ad;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class SearchFragment extends Fragment {
    //androidx.appcompat.widget.SearchView search_view;
    private List<String> list;          // 데이터를 넣은 리스트변수

    private  List<String> l_brand;
    private  List<String> l_name;
    private  List<String> l_price;
    private ListView listView;          // 검색을 보여줄 리스트변수

    private GridView gridView;
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private SearchAdapter adapter;      // 리스트뷰에 연결할 아답터
    private ArrayList<String> arraylistbrand;
    private ArrayList<String> arraylistname;
    private ArrayList<String> arraylistprice;
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
// 리스트를 생성한다.
        list = new ArrayList<String>();
        l_brand = new ArrayList<String>();
        l_name = new ArrayList<String>();
        l_price = new ArrayList<String>();
        // 검색에 사용할 데이터을 미리 저장한다.
        settingList();
        // 리스트의 모든 데이터를 arraylist에 복사한다.// list 복사본을 만든다.
        arraylist = new ArrayList<String>();
        arraylistname = new ArrayList<String>();
        arraylistbrand = new ArrayList<String>();
        arraylistprice = new ArrayList<String>();
        arraylistname.addAll(l_name);
        arraylistbrand.addAll(l_brand);
        arraylistprice.addAll(l_price);
        arraylist.addAll(l_name);

        // 리스트에 연동될 아답터를 생성한다.
        adapter = new SearchAdapter(l_brand,l_name,l_price,container.getContext() );
        // 리스트뷰에 아답터를 연결한다.
        gridView.setAdapter(adapter);


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

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        l_name.clear();
        l_brand.clear();
        l_price.clear();
        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            l_name.addAll(arraylistname);
            l_brand.addAll(arraylistbrand);
            l_price.addAll(arraylistprice);
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
                    l_name.add(arraylistname.get(i));
                    l_brand.add(arraylistbrand.get(i));
                    l_price.add(arraylistprice.get(i));
                }
                if(arraylistbrand.get(i).toLowerCase().contains(charText)){
                    l_name.add(arraylistname.get(i));
                    l_brand.add(arraylistbrand.get(i));
                    l_price.add(arraylistprice.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }
    // 검색에 사용될 데이터를 리스트에 추가한다.
    private void settingList(){
        l_brand.add("1");
        l_brand.add("박지현");
        l_brand.add("수지");
        l_brand.add("남태현");
        l_brand.add("하성운");
        l_brand.add("크리스탈");
        l_brand.add("강승윤");
        l_brand.add("손나은");
        l_brand.add("남주혁");

        l_name.add("루이");
        l_name.add("12");
        l_name.add("슬기");
        l_name.add("이해인");
        l_name.add("고원희");
        l_name.add("설리");
        l_name.add("공명");
        l_name.add("김예림");
        l_name.add("혜리");

        l_price.add("웬디");
        l_price.add("박혜수");
        l_price.add("카이");
        l_price.add("진세연");
        l_price.add("1");
        l_price.add("123");
        l_price.add("1234");
        l_price.add("창모");
        l_price.add("허영지");

    }
}