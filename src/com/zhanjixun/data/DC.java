package com.zhanjixun.data;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import com.zhanjixun.domain.Appraisal;
import com.zhanjixun.domain2.CarOrder;
import com.zhanjixun.interfaces.OnDataReturnListener;
import com.zhanjixun.utils.MyGson;

import com.zhanjixun.utils.LogUtils;

public class DC extends DataCenter {

	private static DC dc;
	private static String TASK = TaskTag.GOOD_ALL;

	private DC() {
	}

	/**
	 * ����ģʽ
	 * 
	 * @return
	 */
	public static DC getInstance() {
		if (dc == null) {
			synchronized (DC.class) {
				if (dc == null)
					dc = new DC();
			}
		}
		return dc;
	}

	/**
	 * �û���¼
	 * 
	 * @param dataReturnListener
	 * @param id
	 * @param pw
	 */
	public void userLogin(OnDataReturnListener dataReturnListener, String id, String pw) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("phoneNumber", id);
		params.put("password", pw);
		getDatasFromServer(TaskTag.LOGIN, "fishshop/user_userLogin.action", params, dataReturnListener);
	}

	/**
	 * �û�ע�����������֤��
	 * 
	 * @param dataReturnListener
	 * @param id
	 */
	public void requestCodeForRegister(OnDataReturnListener dataReturnListener, String id) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("phoneNumber", id);
		getDatasFromServer(TaskTag.REGISTER_CODE, "fishshop/user_smsCodeOfRegister.action", params, dataReturnListener);
	}

	/**
	 * �û���¼
	 * 
	 * @param dataReturnListener
	 * @param phone
	 * @param checkCode
	 * @param password
	 */
	public void register(OnDataReturnListener dataReturnListener, String phone, String checkCode, String password) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("phoneNumber", phone);
		params.put("verifyCode", checkCode);
		params.put("password", password);
		getDatasFromServer(TaskTag.REGISTER, "fishshop/user_addUser.action", params, dataReturnListener);
	}

	/**
	 * �һ��������������֤��
	 * 
	 * @param dataReturnListener
	 * @param id
	 */
	public void requestCodeForFindPassword(OnDataReturnListener dataReturnListener, String id) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("phoneNumber", id);
		getDatasFromServer(TaskTag.REQUEST_MSG_CODE, "fishshop/user_smsCodeOfFound.action", params, dataReturnListener);
	}

	/**
	 * ��֤���һ�����Ķ�����֤��
	 * 
	 * @param dataReturnListener
	 * @param id
	 * @param code
	 */
	public void checkMsgCode(OnDataReturnListener dataReturnListener, String id, String code) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("verifyCode", code);
		params.put("phoneNumber", id);
		getDatasFromServer(TaskTag.CHECK_MSG_CODE, "fishshop/user_confirmSmsVerifyCode.action", params,
				dataReturnListener);
	}

	/**
	 * �һ�����
	 * 
	 * @param dataReturnListener
	 * @param id
	 * @param pw
	 */
	public void findPassword(OnDataReturnListener dataReturnListener, String id, String pw) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("password", pw);
		params.put("phoneNumber", id);
		getDatasFromServer(TaskTag.FIND_PASSWORD, "fishshop/user_findPassword.action", params, dataReturnListener);
	}

	/**
	 * ��ȡ�û������ջ���ַ
	 * 
	 * @param dataReturnListener
	 * @param id
	 */
	public void getAddrees(OnDataReturnListener dataReturnListener, String id) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", id);
		getDatasFromServer(TaskTag.GET_ADDREES, "fishshop/getaddress_getUserAllAddress.action", params,
				dataReturnListener);
	}

	/**
	 * �޸��ջ���ַ
	 * 
	 * @param dataReturnListener
	 * @param id
	 * @param addressId
	 * @param name
	 * @param adsPhone
	 * @param address
	 * @param mail
	 */
	public void changeAddress(OnDataReturnListener dataReturnListener, String id, String addressId, String name,
			String adsPhone, String address, String mail) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("address_id", addressId);
		params.put("UserId", id);
		params.put("consignee", name);
		params.put("address", address);
		params.put("phone", adsPhone);
		params.put("zip_code", mail);
		getDatasFromServer(TaskTag.CHANGE_ADDRESS, "fishshop/getaddress_updateGetAddress.action", params,
				dataReturnListener);
	}

	/**
	 * ��ȡ����������Ʒ
	 * 
	 * @param dataReturnListener
	 * @param categoryId
	 * @param indexPageNum
	 * @param size
	 */
	public void getGoodList(OnDataReturnListener dataReturnListener, String categoryId, int indexPageNum, int size) {
		Map<String, String> params = new IdentityHashMap<String, String>();

		params.put(new String("categoryId"), categoryId);
		params.put(new String("pageInfo.indexPageNum"), indexPageNum + "");
		params.put(new String("pageInfo.size"), size + "");

		getDatasFromServer(TaskTag.GOOD_LIST, "fishshop/category_getNextLevelCategory.action", params,
				dataReturnListener);
	}

	/**
	 * ɾ���ջ���ַ
	 * 
	 * @param dataReturnListener
	 * @param addressId
	 */
	public void deleteAdress(OnDataReturnListener dataReturnListener, String addressId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("getAddressId", addressId);
		getDatasFromServer(TaskTag.DELETE_ADDRESS, "fishshop/getaddress_deleteGetAddress.action", params,
				dataReturnListener);
	}

	/**
	 * ��ȡ��Ʒ�����̼�
	 * 
	 * @param dataReturnListener
	 * @param categoryId
	 * @param page
	 * @param item
	 */
	public void getAllGoodSellers(OnDataReturnListener dataReturnListener, String categoryId, int page, int item) {
		Map<String, String> params = new IdentityHashMap<String, String>();
		params.put(new String("categoryId"), categoryId);
		params.put(new String("pageInfo.indexPageNum"), page + "");
		params.put(new String("pageInfo.size"), item + "");
		params.put("sortType", 1 + "");

		getDatasFromServer(TaskTag.GOOD_SELLER, "fishshop/category_getGoodsShops.action", params, dataReturnListener);
	}

	/**
	 * ��ȡ�̼����۵���Ʒ
	 * 
	 * @param dataReturnListener
	 * @param shopId
	 * @param page
	 * @param item
	 */
	public void getSellerGoods(OnDataReturnListener dataReturnListener, String shopId, int page, int item) {
		Map<String, String> params = new IdentityHashMap<String, String>();
		params.put(new String("shopId"), shopId);
		params.put(new String("pageInfo.indexPageNum"), page + "");
		params.put(new String("pageInfo.size"), item + "");

		getDatasFromServer(TaskTag.SELLER_GOODS, "fishshop/shop_getShopAllGoods.action", params, dataReturnListener);
	}

	/**
	 * ��ȡ������Ʒ
	 * 
	 * @param dataReturnListener
	 */
	public void getHotItems(OnDataReturnListener dataReturnListener) {
		getDatasFromServer(TaskTag.MONTH_HOT, "fishshop/category_getHotCategory.action", null, dataReturnListener);
	}

	/**
	 * ��ȡ�̼�ȫ������
	 * 
	 * @param dataReturnListener
	 * @param Id
	 * @param page
	 * @param item
	 */
	public void getAllComments(OnDataReturnListener dataReturnListener, String Id, int page, int item) {
		Map<String, String> params = new IdentityHashMap<String, String>();
		params.put(new String("shopId"), Id);
		params.put(new String("pageInfo.indexPageNum"), page + "");
		params.put(new String("pageInfo.size"), item + "");

		getDatasFromServer(TaskTag.COMMENT_DETAIL, "fishshop/comment_getShopComment.action", params,
				dataReturnListener);
	}

	/**
	 * ��ȡ�̼Һ���
	 * 
	 * @param dataReturnListener
	 * @param Id
	 * @param page
	 * @param item
	 */
	public void getGoodComments(OnDataReturnListener dataReturnListener, String Id, int page, int item) {
		Map<String, String> params = new IdentityHashMap<String, String>();
		params.put(new String("shopId"), Id);
		params.put(new String("pageInfo.indexPageNum"), page + "");
		params.put(new String("pageInfo.size"), item + "");

		getDatasFromServer(TaskTag.COMMENT_DETAIL, "fishshop/comment_getGoodComment.action", params,
				dataReturnListener);
	}

	/**
	 * ��ȡ�̼�����
	 * 
	 * @param dataReturnListener
	 * @param Id
	 * @param page
	 * @param item
	 */
	public void getMidComments(OnDataReturnListener dataReturnListener, String Id, int page, int item) {
		Map<String, String> params = new IdentityHashMap<String, String>();
		params.put(new String("shopId"), Id);
		params.put(new String("pageInfo.indexPageNum"), page + "");
		params.put(new String("pageInfo.size"), item + "");

		getDatasFromServer(TaskTag.COMMENT_DETAIL, "fishshop/comment_getMidComment.action", params, dataReturnListener);
	}

	/**
	 * ��ȡ�̼Ҳ���
	 * 
	 * @param dataReturnListener
	 * @param Id
	 * @param page
	 * @param item
	 */
	public void getBadComments(OnDataReturnListener dataReturnListener, String Id, int page, int item) {
		Map<String, String> params = new IdentityHashMap<String, String>();
		params.put(new String("shopId"), Id);
		params.put(new String("pageInfo.indexPageNum"), page + "");
		params.put(new String("pageInfo.size"), item + "");

		getDatasFromServer(TaskTag.COMMENT_DETAIL, "fishshop/comment_getBedComment.action", params, dataReturnListener);
	}

	/**
	 * �����ύ
	 * 
	 * @author Imissyou
	 * @param appraiseActivity
	 * @param submitData
	 * @return void
	 */
	public void AddAppraise(OnDataReturnListener dataReturnListener, Map<String, Object> submitData) {
		Map<String, String> params = new IdentityHashMap<String, String>();
		params.put("shopId", submitData.get("shopId").toString());
		params.put("userId", submitData.get("userId").toString());
		params.put("orderId", submitData.get("orderId").toString());
		params.put("commentType", submitData.get("commentType").toString());
		params.put("content", submitData.get("content").toString());
		params.put("weightQuality", submitData.get("weightQuality").toString());
		params.put("freshQuality", submitData.get("freshQuality").toString());
		params.put("speedQuality", submitData.get("speedQuality").toString());
		params.put("anonymity", submitData.get("anonymity").toString());
		getDatasFromServer(TaskTag.APPRAISE_COMMIT, "fishshop/comment_addComment.action", params, dataReturnListener);
	}

	/**
	 * ��ȡ�û�������
	 * 
	 * @param dataReturnListener
	 * @param id
	 * @param page
	 * @param item
	 */
	public void getMyComment(OnDataReturnListener dataReturnListener, String id, int page, int item) {
		Map<String, String> params = new IdentityHashMap<String, String>();
		params.put("userId", id);
		params.put("pageInfo.indexPageNum", page + "");
		params.put("pageInfo.size", item + "");
		getDatasFromServer(TaskTag.MY_COMMENT, "fishshop/comment_getUserComment.action", params, dataReturnListener);
	}

	/**
	 * ��ȡ�û�ȫ������
	 * 
	 * @param dataReturnListener
	 * @param userId
	 * @param index
	 * @param pageSize
	 */
	public void getAllOrder(OnDataReturnListener dataReturnListener, String userId, int index, int pageSize) {
		Map<String, String> params = new IdentityHashMap<String, String>();
		params.put("userId", userId);
		params.put("pageInfo.indexPageNum", index + "");
		params.put("pageInfo.size", pageSize + "");
		getDatasFromServer(TaskTag.ORDER_ALL, "fishshop/orders_getAllUserOrders.action", params, dataReturnListener);
	}

	/**
	 * ��ȡ�û�δ�����
	 * 
	 * @param dataReturnListener
	 * @param userId
	 * @param index
	 * @param pageSize
	 */
	public void getUnPayOrder(OnDataReturnListener dataReturnListener, String userId, int index, int pageSize) {
		Map<String, String> params = new IdentityHashMap<String, String>();
		params.put("userId", userId);
		params.put("pageInfo.indexPageNum", index + "");
		params.put("pageInfo.size", pageSize + "");
		getDatasFromServer(TaskTag.ORDER_UN_PAY, "fishshop/orders_getUnPayOrders.action", params, dataReturnListener);
	}

	/**
	 * ��ȡ�û�δ�ջ�����
	 * 
	 * @param dataReturnListener
	 * @param userId
	 * @param index
	 * @param pageSize
	 */
	public void getUngetOrder(OnDataReturnListener dataReturnListener, String userId, int index, int pageSize) {
		Map<String, String> params = new IdentityHashMap<String, String>();
		params.put("userId", userId);
		params.put("pageInfo.indexPageNum", index + "");
		params.put("pageInfo.size", pageSize + "");
		getDatasFromServer(TaskTag.ORDER_UN_GET, "fishshop/orders_getUngetOrders.action", params, dataReturnListener);
	}

	/**
	 * ��ȡ�û�δ���Ͷ���
	 * 
	 * @param dataReturnListener
	 * @param userId
	 * @param index
	 * @param pageSize
	 */
	public void getUnSentOrder(OnDataReturnListener dataReturnListener, String userId, int index, int pageSize) {
		Map<String, String> params = new IdentityHashMap<String, String>();
		params.put("userId", userId);
		params.put("pageInfo.indexPageNum", index + "");
		params.put("pageInfo.size", pageSize + "");
		getDatasFromServer(TaskTag.ORDER_UN_SENT, "fishshop/orders_getUnSentOrders.action", params, dataReturnListener);
	}

	/**
	 * ��ȡ�û�δ���۶���
	 * 
	 * @param dataReturnListener
	 * @param userId
	 * @param index
	 * @param pageSize
	 */
	public void getUnCommentOrder(OnDataReturnListener dataReturnListener, String userId, int index, int pageSize) {
		Map<String, String> params = new IdentityHashMap<String, String>();
		params.put("userId", userId);
		params.put("pageInfo.indexPageNum", index + "");
		params.put("pageInfo.size", pageSize + "");
		getDatasFromServer(TaskTag.ORDER_UN_COMMENT, "fishshop/orders_getUnCommentOrders.action", params,
				dataReturnListener);
	}

	/**
	 * ��ȡ������Ϣ
	 * 
	 * @param dataReturnListener
	 * @param orderId
	 */
	public void getOrderInfo(OnDataReturnListener dataReturnListener, String orderId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("ordersId", orderId);
		getDatasFromServer(TaskTag.ORDER_INFO, "orders_getPointOrders.action", params, dataReturnListener);
	}

	/**
	 * @author Imissyou
	 * @param string
	 * @param order_information_Activity
	 * @param order_id
	 *            ֧�����ܽӿ�
	 * @return void
	 */
	public void payOrders(String taskTag, OnDataReturnListener dataReturnListener, String order_id) {

		Map<String, String> params = new IdentityHashMap<String, String>();
		params.put("odersId", order_id);
		LogUtils.d(params.toString());
		getDatasFromServer(taskTag, "fishshop/orders_payForOrdersWithAlipay.action", params, dataReturnListener);
	}

	/**
	 * ��ȡ�̼����۸�Ҫ
	 * 
	 * @param dataReturnListener
	 * @param Id
	 */
	public void getCommentsSummary(OnDataReturnListener dataReturnListener, String Id) {
		Map<String, String> params = new IdentityHashMap<String, String>();
		params.put(new String("shopId"), Id);

		getDatasFromServer(TaskTag.COMMENT_SUMMARY, "fishshop/shop_getShopCommentInfo.action", params,
				dataReturnListener);
	}

	/**
	 * ��ȡ�̼һ�����Ϣ
	 * 
	 * @param dataReturnListener
	 * @param sellerId
	 */
	public void getSellerBaseInfo(OnDataReturnListener dataReturnListener, String sellerId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("shopId", sellerId);
		getDatasFromServer(TaskTag.SELLER_INFO, "fishshop/shop_getShop.action", params, dataReturnListener);
	}

	/**
	 * ���ﳵ�ύ����
	 * 
	 * @param dataReturnListener
	 * @param addressId
	 * @param userId
	 * @param shopId
	 * @param buyersName
	 * @param shopName
	 * @param seaRecordId
	 * @param order
	 */
	public void commitOrder(OnDataReturnListener dataReturnListener, CarOrder carOrder, String addressId) {

		Map<String, String> params = new HashMap<String, String>();
		String json = MyGson.getInstance().toJson(carOrder);
		params.put("getAddressId", addressId);
		params.put("orders", json);
		LogUtils.v(json);
		getDatasFromServer(TaskTag.COMMIT_ORDER, "orders_addOrders.action", params, dataReturnListener);
	}

	/**
	 * ��ȡ�����Ŀ�ݺ�
	 * 
	 * @param dataReturnListener
	 * @param ordersId
	 */
	public void orderPostCode(OnDataReturnListener dataReturnListener, String ordersId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("ordersId", ordersId);
		getDatasFromServer(TaskTag.ORDER_POST_CODE, "orders_getOrderNumber.action", params, dataReturnListener);
	}

	/**
	 * ��ȡ������Ϣ
	 * 
	 * @param dataReturnListener
	 * @param postid
	 * @param type
	 */
	public void getLogistic(OnDataReturnListener dataReturnListener, String postid) {
		// http://www.kuaidi100.com/query?type=zhongtong&postid=719121392152
		Map<String, String> params = new HashMap<String, String>();
		params.put("type", "zhongtong");
		params.put("postid", postid);
		getLogistics(TaskTag.LOGISTIC, params, dataReturnListener);
	}

	/**
	 * �õ�ָ���㻧��������Ϣ
	 * 
	 * @param dataReturnListener
	 * @param Id
	 */
	public void getFishmanDetialInfo(OnDataReturnListener dataReturnListener, String Id) {
		Map<String, String> params = new IdentityHashMap<String, String>();
		params.put(new String("shopId"), Id);
		getDatasFromServer(TaskTag.FISHMAN_DETAIL, "fishshop/fishman_getFishmanByShop.action", params,
				dataReturnListener);
	}

	/**
	 * �õ�ָ����ֳ����������Ϣ
	 * 
	 * @param dataReturnListener
	 * @param Id
	 */
	public void getFarmerDetialInfo(OnDataReturnListener dataReturnListener, String Id) {
		Map<String, String> params = new IdentityHashMap<String, String>();
		params.put(new String("shopId"), Id);

		getDatasFromServer(TaskTag.FARMER_DETAIL, "fishshop/farmers_getFarmerByShop.action", params,
				dataReturnListener);
	}

	/**
	 * ֧���ӿ�
	 * 
	 * @author Imissyou
	 * @param payAction
	 * @param channel
	 * @param ordersId
	 */
	public void pay(OnDataReturnListener dataReturnListener, String channel, String ordersId) {
		Map<String, String> params = new HashMap<>();
		params.put("channel", channel);
		params.put("ordersId", ordersId);
		getDatasFromServer(TaskTag.PAY, "orders_payForOrdersWithAlipay.action", params, dataReturnListener);
	}

	/**
	 * ȷ���ջ�
	 * 
	 * @param dataReturnListener
	 * @param orderId
	 */
	public void ensureGet(OnDataReturnListener dataReturnListener, String orderId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("ordersId", orderId);
		getDatasFromServer(TaskTag.ENSUREGET, "orders_realGetOrders.action", params, dataReturnListener);
	}

	/**
	 * �����ύ
	 * 
	 * @author Imissyou
	 * @param appraiseActivity
	 * @param submitData
	 */
	public void AddAppraise(OnDataReturnListener dataReturnListener, Appraisal appraisal) {
		Map<String, String> params = new IdentityHashMap<String, String>();
		params.put("shopId", appraisal.getShop_id());
		params.put("userId", appraisal.getUser_id());
		params.put("ordersId", appraisal.getOrder_id());
		params.put("commentType", appraisal.getConmment_kind() + "");
		params.put("content", appraisal.getConmment_text());
		params.put("weightQuality", appraisal.getConmment_weight() + "");
		params.put("freshQuality", appraisal.getConmment_fresh() + "");
		params.put("speedQuality", appraisal.getConmment_speed() + "");
		params.put("anonymity", appraisal.getAnonymous() + "");
		getDatasFromServer(TaskTag.APPRAISE_COMMIT, "fishshop/comment_addComment.action", params, dataReturnListener);
	}

	/**
	 * ����ջ���ַ
	 * 
	 * @param dataReturnListener
	 * @param phone
	 * @param name
	 * @param adsPhone
	 * @param address
	 * @param mail
	 */
	public void addAddress(OnDataReturnListener dataReturnListener, String phone, String name, String adsPhone,
			String address, String mail) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", phone);
		params.put("userName", name);
		params.put("address", address);
		params.put("phone", adsPhone);
		params.put("postcode", mail);

		getDatasFromServer(TaskTag.ADD_ADDRESS, "fishshop/getaddress_addGetAddress.action", params, dataReturnListener);
	}

	/**
	 * �޸��ջ���ַ
	 * 
	 * @param dataReturnListener
	 * @param addressId
	 * @param phone
	 * @param name
	 * @param adsPhone
	 * @param address
	 * @param mail
	 */
	public void updataAddress(OnDataReturnListener dataReturnListener, String addressId, String phone, String name,
			String adsPhone, String address, String mail) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("getAddressId", addressId);
		params.put("userId", phone);
		params.put("userName", name);
		params.put("address", address);
		params.put("phone", adsPhone);
		params.put("postcode", mail);
		getDatasFromServer(TaskTag.UPDATA_ADDRESS, "fishshop/getaddress_addGetAddress.action", params,
				dataReturnListener);
	}

	/**
	 * �޸�Ĭ���ջ���ַ
	 * 
	 * @param dataReturnListener
	 * @param addressId
	 * @param userId
	 */
	public void defultaAdress(OnDataReturnListener dataReturnListener, String addressId, String userId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("getAddressId", addressId);
		// getaddress_updateUserAddress.action
		getDatasFromServer(TaskTag.DEFULT_AADRESS, "getaddress_updateUserAddress.action", params, dataReturnListener);
	}

	/**
	 * �õ���Ĭ���ջ���ַ
	 * 
	 * @param dataReturnListener
	 * @param userId
	 */
	public void getDefultAddress(OnDataReturnListener dataReturnListener, String userId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		getDatasFromServer(TaskTag.GET_DEFULT_ADDRESS, "getaddress_getDefaultAddress.action", params,
				dataReturnListener);
	}

	/**
	 * �õ��������ʷ�
	 * 
	 * @param dataReturnListener
	 * @param carOrder
	 * @param addressId
	 */
	public void getOrdersPostage(OnDataReturnListener dataReturnListener, CarOrder carOrder, String addressId) {
		Map<String, String> params = new HashMap<String, String>();
		String json = MyGson.getInstance().toJson(carOrder);
		params.put("orders", json);
		params.put("getAddressId", addressId);
		LogUtils.v(json);
		getDatasFromServer(TaskTag.GET_ORDERS_POSTAGE, "orders_getOrdersPostage.action", params, dataReturnListener);
	}

	/**
	 * �����û���
	 */
	public void changeUserName(OnDataReturnListener dataReturnListener, String userId, String userName) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("userName", userName);
		getDatasFromServer(TaskTag.CHANGE_USER_NAME, "fishshop/user_updateUser.action", params, dataReturnListener);
	}

	/**
	 * �����û�����
	 */
	public void changeUserPassword(OnDataReturnListener dataReturnListener, String userId, String password,
			String newPassword) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("password", password);
		params.put("newPassword", newPassword);
		getDatasFromServer(TaskTag.CHANGE_USER_PASSWORD, "user_updatePassword.action", params, dataReturnListener);
	}

	/**
	 * �����û��ֻ�
	 */
	public void changeUserPhone(OnDataReturnListener dataReturnListener, int userId, String userName) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId + "");
		params.put("userName", userName);
		getDatasFromServer(TaskTag.CHANGE_USER_NAME, "fishshop/user_updateUser.action", params, dataReturnListener);
	}

	/**
	 * ��ȡָ����Ʒ�б�
	 * 
	 * @param dataReturnListener
	 * @param pageSize
	 * @param pageIndex
	 */
	public void getGoodDatailWild(OnDataReturnListener dataReturnListener, String taskTag, int pageIndex, int pageSize,
			int typenumber , String categoryId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(new String("pageInfo.indexPageNum"), pageIndex + "");
		params.put(new String("pageInfo.size"), pageSize + "");
		params.put("categoryId", categoryId);
		params.put("sortType", typenumber + "");
		LogUtils.i(pageIndex + "");
		
		switch (taskTag) {
		case TaskTag.GOOD_ALL:
			TASK = TaskTag.GOOD_ALL;
			getDatasFromServer(TASK, "fishshop/category_getGoodsShops.action", params, dataReturnListener);
			break;
		case TaskTag.GOOD_WILD:
			TASK = TaskTag.GOOD_WILD;
			getDatasFromServer(TASK, "fishshop/category_getGoodsFishShops.action", params, dataReturnListener);
			break;
		case TaskTag.GOOD_BREAD:
			TASK = TaskTag.GOOD_BREAD;
			getDatasFromServer(TASK, "fishshop/category_getGoodsFarmerShops.action", params, dataReturnListener);
			break;
		default:
			this.getGoodDatailWild(dataReturnListener, TASK, pageIndex, pageSize, typenumber, categoryId);
			break;
		}
		// switch (type) {
		// //���е���Ʒ
		// case TaskTag.GOOD_ALL:
		// getDatasFromServer(TaskTag.GOOD_ALL, URL, params,
		// dataReturnListener);
		// break;
		// //���е�Ұ����Ʒ
		// case TaskTag.GOOD_WILD:
		// getDatasFromServer(TaskTag.GOOD_WILD, URL, params,
		// dataReturnListener);
		// break;
		// //���е���ֳ��Ʒ
		// case TaskTag.GOOD_BREAD:
		// getDatasFromServer(TaskTag.GOOD_BREAD, URL, params,
		// dataReturnListener);
		// break;
		// //�����ۺ�������Ʒ
		// case TaskTag.COMPREHENSIVE_RANKING:
		// getDatasFromServer(TaskTag.COMPREHENSIVE_RANKING, URL, params,
		// dataReturnListener);
		// break;
		// //�������������Ʒ
		// case TaskTag.COMMENT_HIGHEST:
		// getDatasFromServer(TaskTag.COMMENT_HIGHEST, URL, params,
		// dataReturnListener);
		// break;
		// //�������ͼ������Ʒ
		// case TaskTag.SEND_PRICE:
		// getDatasFromServer(TaskTag.SEND_PRICE, URL, params,
		// dataReturnListener);
		// break;
		// //�����������
		// case TaskTag.SALES_VOLUME:
		// getDatasFromServer(TaskTag.SALES_VOLUME, URL, params,
		// dataReturnListener);
		// break;
		// default:
		// getDatasFromServer(TaskTag.GOOD_ALL,
		// "fishshop/category_getGoodsShops.action", params,
		// dataReturnListener);
		// break;
		// }
	}
}
