package org.androidpn.adpater;

import java.util.List;

import org.androidpn.data.Message;
import org.androidpn.demoapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


/**
 * ��ϢListView��Adapter
 * 
 * @author way
 */
public class ChatMsgViewAdapter extends BaseAdapter {

	public static interface IMsgViewType {
		int IMVT_COM_MSG = 0;// �յ��Է�����Ϣ
		int IMVT_TO_MSG = 1;// �Լ����ͳ�ȥ����Ϣ
	}

	private static final int ITEMCOUNT = 2;// ��Ϣ���͵�����
	private List<Message> coll;// ��Ϣ��������
	private LayoutInflater mInflater;

	public ChatMsgViewAdapter(Context context, List<Message> coll) {
		this.coll = coll;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return coll.size();
	}

	public Object getItem(int position) {
		return coll.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	/**
	 * �õ�Item�����ͣ��ǶԷ�����������Ϣ�������Լ����ͳ�ȥ��
	 */
	public int getItemViewType(int position) {
		Message msg = coll.get(position);

		if (msg.isComMeg()) {//�յ�����Ϣ
			return IMsgViewType.IMVT_COM_MSG;
		} else {//�Լ����͵���Ϣ
			return IMsgViewType.IMVT_TO_MSG;
		}
	}

	/**
	 * Item���͵�����
	 */
	public int getViewTypeCount() {
		return ITEMCOUNT;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		Message msg = coll.get(position);
		boolean isComMsg = msg.isComMeg();

		ViewHolder viewHolder = null;
		if (convertView == null) {
			if (isComMsg) {
				convertView = mInflater.inflate(
						R.layout.chatting_item_msg_text_left, null);
			} else {
				convertView = mInflater.inflate(
						R.layout.chatting_item_msg_text_right, null);
			}

			viewHolder = new ViewHolder();
			viewHolder.tvSendTime = (TextView) convertView
					.findViewById(R.id.tv_sendtime);
			viewHolder.tvUserName = (TextView) convertView
					.findViewById(R.id.tv_username);
			viewHolder.tvContent = (TextView) convertView
					.findViewById(R.id.tv_chatcontent);
			viewHolder.isComMsg = isComMsg;

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tvSendTime.setText(msg.getSentDate());
		//if(isComMsg)
			//viewHolder.tvUserName.setText(msg.getTo());
		//else
			viewHolder.tvUserName.setText(msg.getFrom());
		viewHolder.tvContent.setText(msg.getBody());
		return convertView;
	}

	static class ViewHolder {
		public TextView tvSendTime;
		public TextView tvUserName;
		public TextView tvContent;
		public boolean isComMsg = true;
	}

}
