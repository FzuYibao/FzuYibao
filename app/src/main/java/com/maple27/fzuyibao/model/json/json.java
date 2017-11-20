JSONObject  dataJson=new JSONObject("ÄãµÄJsonÊý¾Ý¡°);
JSONObject  response=dataJson.getJSONObject("response");
JSONArray data=response.getJSONArray("data");
JSONObject info=data.getJSONObject(0);
String province=info.getString("province");
String city=info.getString("city");
String district=info.getString("district");
String address=info.getString("address");
 System.out.println(province+city+district+address);

