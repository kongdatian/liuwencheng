package json;
//download by http///www.codesc.net
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class JsonConvert {

	/**
	 * @param args
	 */

    public static String replaceSpecialStr(String str) {
        String repl = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            repl = m.replaceAll("");
        }
        return repl;
    }
	public static void main(String[] args) {
		
		String strjson="";
		BufferedReader brname;
		try {
			brname = new BufferedReader(new FileReader("src/json/sample_command.json"));// 读取NAMEID对应值
			String sname = null;
			while ((sname = brname.readLine()) != null) {
				// System.out.println(sname);
				strjson = strjson+sname;// 将对应value添加到链表存储
			}
			strjson = replaceSpecialStr(strjson);
			BufferedWriter bwr = new BufferedWriter(new FileWriter("src/json/sample_command-new.json"));// 输出新的json文件
			bwr.write(strjson);
			bwr.flush();
			bwr.close();
			brname.close();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		String[][] User= {{"6236609999","马丁","普卡","9860"},{"6630009999","王立","金卡","48860"},{"8230009999","李想","白金卡","98860"},{"9230009999","张三","钻石卡","198860"}};
		String[][] productinfo={{"001001","世园会五十国钱币册","998.00"},{"001002","2019北京世园会纪念银章大全40g","1380.00"},{"003001","招财进宝","1580.00"},{"003002","水晶之恋","980.00"},{"002002","中国经典钱币套装","998.00"},{"002001","守扩之羽比翼双飞4.8g","1080.00"},{"002003","中国银象棋12g","698.00"}};
		
		System.out.println(User[0][0]);
	    String PrintText = "方鼎银行贵金属购买凭证\n\n";
		// 读取原始json文件并进行操作和输出
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/json/sample_command-new.json"));// 读取原始json文件
			BufferedWriter bw = new BufferedWriter(new FileWriter("src/json/sample_result.txt"));// 输出新的文件
			String s = null, ws = null;
			while ((s = br.readLine()) != null) {
				try {
					JSONObject dataJson = new JSONObject(s);// 创建一个包含原始json串的json对象
					JSONArray items = dataJson.getJSONArray("items");
					JSONArray payments = dataJson.getJSONArray("payments");
					JSONObject paymentslist = payments.getJSONObject(0);
					
					
					String orderId = dataJson.getString("orderId");
					String memberId = dataJson.getString("memberId");
					String createTime = dataJson.getString("createTime");
					String discountCards = dataJson.getString("discountCards");
					
					
					String type = paymentslist.getString("type");
					String amount = paymentslist.getString("amount");
					int usernum=0;
					for (int j = 0; j < 4; j++) {
						if (memberId.equals(User[j][0])){
							usernum = j;
						}
					}
					PrintText = PrintText + "销售单号："+orderId+" 日期："+createTime+"\n"+"客户卡号："+memberId+" 会员姓名："+User[usernum][1]+" 客户等级："+User[usernum][2]+"  累计积分："+User[usernum][3];
					PrintText = PrintText + "\n\n";
					PrintText = PrintText + "商品及数量           单价         金额";
					String productdiscount = "";
					int prunum=0;
					int prucnum=0;
					for (int i = 0; i < items.length(); i++) {				
						JSONObject info = items.getJSONObject(i);
						String product = info.getString("product");// 读产品
						System.out.println(product);
						//System.out.println( productinfo[0][0]);
						for(int p=0;p<7;p++) {
							if(product.equals(productinfo[p][0])) {
								prucnum = p;	
							}
							
						}
						productdiscount = productdiscount+product+"\n";
						PrintText = PrintText +"\n("+product+")"+productinfo[prucnum][1]+"x";
						//System.out.println(usernum);
						String productamount = info.getString("amount");// 数量
						double Acount = 0;
						PrintText = PrintText + productamount+" "+productinfo[prucnum][2]+" "+String.valueOf(Double.parseDouble(productinfo[prucnum][2])*Integer.valueOf(productamount));
						//System.out.println(productamount);


					}
					PrintText = PrintText +"\n合计：10624.00\n";
					PrintText = PrintText +"优惠清单：\n";
					PrintText = PrintText +productdiscount;
					PrintText = PrintText +"优惠合计：764.00\n\n";
					PrintText = PrintText +"应收合计："+amount+"\n";
					PrintText = PrintText +"收款：\n "+type+"："+amount;
					PrintText = PrintText +"客户等级与积分：\n";
					PrintText = PrintText +" 新增积分：\n";
					ws = PrintText.toString();
					System.out.println(ws);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			bw.write(ws);
			// bw.newLine();

			bw.flush();
			br.close();
			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
