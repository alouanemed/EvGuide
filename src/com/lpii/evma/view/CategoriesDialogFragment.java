//package com.lpii.evma.view;
//
//import java.util.ArrayList;
//
//import android.widget.ListView;
//
//public class CategoriesDialogFragment extends SherlockDialogFragment {
//
//    ListView dialog_ListView;
//    static CheckBox chk_all;
//    static SelectViewHolder viewHolder;
//
//    private static ArrayAdapter<mItems> listAdapter;
//    static ArrayList<String> checked = new ArrayList<String>();
//
//    protected static CharSequence[] _categories = { "Amusement Park",
//            "Bird Sanctuary", "Wild Life", "River", "Hill Station", "Temple",
//            "Rafting", "Fishing", "Hiking", "Museums" };
//
//    protected static boolean[] _selections = new boolean[_categories.length];
//    PlacesListAdapter adapter;
//    ListView listView;
//    Button dialog_ok;
//
//    static int TAG = 0;
//    static mItems categories;
//
//    static mItems orig;
//
//    public static CategoriesDialogFragment newInstance(int title) {
//        CategoriesDialogFragment frag = new CategoriesDialogFragment();
//        Bundle args = new Bundle();
//        args.putInt("title", title);
//        frag.setArguments(args);
//        return frag;
//    }
//
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        final Dialog dialog = new Dialog(MainActivity.context);
//        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setTitle("Categories");
//        dialog.setContentView(R.layout.dialog);
//        dialog_ok = (Button) dialog.findViewById(R.id.button_category_ok);
//
//        dialog_ok.setOnClickListener(new OnClickListener() {
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog_ListView = (ListView) dialog.findViewById(R.id.listViewDialog);
//
//        dialog_ListView
//                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View item,
//                            int position, long id) {
//                        categories = listAdapter.getItem(position);
//
//                        orig = listAdapter.getItem(position);
//
//                        categories.toggleChecked();
//
//                        viewHolder = (SelectViewHolder) item.getTag();
//                        viewHolder.getCheckBox().setChecked(
//                                categories.isChecked());
//
//                        if (!viewHolder.getCheckBox().isChecked()) {
//                            TAG = 1;
//                            chk_all.setChecked(false);
//
//                        }
//                        TAG = 0;
//
//                        /*
//                         * if (viewHolder.getCheckBox().isChecked()) {
//                         * 
//                         * TAG = 0; }
//                         */
//
//                        for (int i = 0; i < _categories.length; i++) {
//                            categories = listAdapter.getItem(i);
//                            if (!categories.isChecked()) {
//                                break;
//                            }
//
//                            if (i == _categories.length - 1) {
//                                TAG = 1;
//                                chk_all.setChecked(true);
//                                TAG = 0;
//                            }
//                        }
//                    }
//                });
//
//        chk_all = (CheckBox) dialog.findViewById(R.id.checkBoxAll);
//        chk_all.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView,
//                    boolean isChecked) {
//                if (TAG != 1) {
//                    if (isChecked) {
//                        for (int i = 0; i < listAdapter.getCount(); i++) {
//                            categories = listAdapter.getItem(i);
//                            categories.setChecked(true);
//                        }
//                        listAdapter.notifyDataSetChanged();
//                    } else {
//                        for (int i = 0; i < listAdapter.getCount(); i++) {
//                            categories = listAdapter.getItem(i);
//                            categories.setChecked(false);
//                        }
//                        listAdapter.notifyDataSetChanged();
//                    }
//                }
//                if (TAG == 1) {
//                    TAG = 0;
//                }
//            }
//        });
//
//        // itemss = (mItems[]) onRetainNonConfigurationInstance();
//        ArrayList<mItems> CategoryList = new ArrayList<mItems>();
//
//        CategoryList.add(new mItems("Amusement Park"));
//        CategoryList.add(new mItems("Bird Sanctuary"));
//        CategoryList.add(new mItems("Wild Life"));
//        CategoryList.add(new mItems("River"));
//        CategoryList.add(new mItems("Hill Station"));
//        CategoryList.add(new mItems("Temple"));
//        CategoryList.add(new mItems("Rafting"));
//        CategoryList.add(new mItems("Fishing"));
//        CategoryList.add(new mItems("Hiking"));
//        CategoryList.add(new mItems("Museums"));
//
//        // Set our custom array adapter as the ListView's adapter.
//        listAdapter = new SelectArralAdapter(MainActivity.context, CategoryList);
//        dialog_ListView.setAdapter(listAdapter);
//
//        return dialog;
//
//    }
//
//    private static class SelectArralAdapter extends ArrayAdapter<mItems> {
//        private LayoutInflater inflater;
//
//        public SelectArralAdapter(Context context, List<mItems> planetList) {
//            super(context, R.layout.dialog_row, R.id.rowTextView, planetList);
//            // Cache the LayoutInflate to avoid asking for a new one each
//            // time.
//            inflater = LayoutInflater.from(context);
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            // Planet to display
//            mItems planet = (mItems) this.getItem(position);
//
//            // The child views in each row.
//            CheckBox checkBox;
//            TextView textView;
//
//            // Create a new row view
//            if (convertView == null) {
//                convertView = inflater.inflate(R.layout.dialog_row, null);
//
//                // Find the child views.
//                textView = (TextView) convertView
//                        .findViewById(R.id.rowTextView);
//                checkBox = (CheckBox) convertView.findViewById(R.id.CheckBox01);
//                // Optimization: Tag the row with it's child views, so we
//                // don't
//                // have to
//                // call findViewById() later when we reuse the row.
//                convertView.setTag(new SelectViewHolder(textView, checkBox));
//                // If CheckBox is toggled, update the planet it is tagged
//                // with.
//                checkBox.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View v) {
//                        System.out.println("uffff");
//                        CheckBox cb = (CheckBox) v;
//                        mItems row_view = (mItems) cb.getTag();
//                        row_view.setChecked(cb.isChecked());
//
//                        TAG = 1;
//                        chk_all.setChecked(false);
//                        TAG = 0;
//
//                        for (int i = 0; i < _categories.length; i++) {
//                            row_view = listAdapter.getItem(i);
//                            if (!row_view.isChecked()) {
//                                break;
//                            }
//
//                            if (i == _categories.length - 1) {
//                                TAG = 1;
//                                chk_all.setChecked(true);
//                                TAG = 0;
//                            }
//                        }
//                    }
//                });
//
//            }
//            // Reuse existing row view
//            else {
//                // Because we use a ViewHolder, we avoid having to call
//                // findViewById().
//                SelectViewHolder viewHolder = (SelectViewHolder) convertView
//                        .getTag();
//                checkBox = viewHolder.getCheckBox();
//                textView = viewHolder.getTextView();
//            }
//
//            // Tag the CheckBox with the Planet it is displaying, so that we
//            // can
//            // access the planet in onClick() when the CheckBox is toggled.
//            checkBox.setTag(planet);
//            // Display planet data
//            checkBox.setChecked(planet.isChecked());
//            textView.setText(planet.getName());
//            return convertView;
//        }
//    }
//}