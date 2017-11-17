public class Book{
    private String id;
    private String name;
    private double price;
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public String getPrice(){
        return this.price;
    }
    @Override
    public String toString() {
        return this.name + "价格：" + this.price;
    }
}
//解析上述Json字符串的方法:
private void parseEasyJson(String json){
    book= new ArrayList<Book>();
    try{
        JSONArray jsonArray = new JSONArray(json);
        for(int i = 0;i < jsonArray.length();i++){
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            Book book = new Book();
            book.setId(i+"");
            book.setName(jsonObject.getString("name"));
            book.setPrice(jsonObject.getString("price"));
            book.add(book);
        }
    }catch (Exception e){e.printStackTrace();}
}
//复杂的json字符串的解析：
private void parseDiffJson(String json) {
    try {
        JSONObject jsonObject1 = new JSONObject(json);
        Log.e("Json", json);
        JSONArray jsonArray = jsonObject1.getJSONArray("ch");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            //取出name
            String sname = jsonObject.getString("names");
            JSONArray jarray1 = jsonObject.getJSONArray("data");
            JSONArray jarray2 = jsonObject.getJSONArray("times");
            Log.e("Json", sname);
            Log.e("Json", jarray1.toString());
            Log.e("Json", jarray2.toString());
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

}