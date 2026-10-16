package com.example.smartpantry;
import android.content.Context;
import android.content.Intent;
import android.view.*;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.VH>{
    Context context; List<PantryItem> list; DatabaseHelper db;
    public PantryAdapter(Context c,List<PantryItem> l,DatabaseHelper d){context=c;list=l;db=d;}
    @Override public VH onCreateViewHolder(ViewGroup p,int v){
        return new VH(LayoutInflater.from(context).inflate(R.layout.row_pantry,p,false));
    }
    @Override public void onBindViewHolder(VH h,int pos){
        PantryItem x=list.get(pos);
        h.name.setText(x.name); h.qty.setText(x.quantity+" "+x.unit);
        h.expiry.setText(x.expiry==null||x.expiry.isEmpty()?"No expiry":"Expires: "+x.expiry);
        h.itemView.setOnClickListener(v->{ Intent i=new Intent(context,AddEditIngredientActivity.class);
            i.putExtra("id",x.id); context.startActivity(i);});
        h.delete.setOnClickListener(v->{db.deletePantry(x.id);list.remove(pos);notifyDataSetChanged();});
    }

    @Override public int getItemCount(){return list.size();}
    static class VH extends RecyclerView.ViewHolder{
        TextView name,qty,expiry; ImageButton delete;
        VH(View v){super(v);name=v.findViewById(R.id.rowName);qty=v.findViewById(R.id.rowQty);
            expiry=v.findViewById(R.id.rowExpiry);delete=v.findViewById(R.id.rowDelete);}
    }
}
