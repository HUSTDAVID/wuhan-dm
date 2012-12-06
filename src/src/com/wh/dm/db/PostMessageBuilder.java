
package com.wh.dm.db;

import com.wh.dm.type.PostMessage;

import android.content.ContentValues;
import android.database.Cursor;

public class PostMessageBuilder extends DatabaseBuilder<PostMessage> {

    private static final String MESSAGE_ID = "id";
    private static final String MESSAGE_TITLE = "title";
    private static final String MESSAGE_MID = "mid";
    private static final String MESSAGE_MNAME = "mname";
    private static final String MESSAGE_PID = "pid";
    private static final String MESSAGE_TEMP = "temp";
    private static final String MESSAGE_UID = "uid";
    private static final String MESSAGE_IS_READ = "is_read";

    @Override
    public PostMessage build(Cursor c) {

        PostMessage message = new PostMessage();
        int columnId = c.getColumnIndex(MESSAGE_ID);
        int columnTitle = c.getColumnIndex(MESSAGE_TITLE);
        int columnMid = c.getColumnIndex(MESSAGE_MID);
        int columnMname = c.getColumnIndex(MESSAGE_MNAME);
        int columnPid = c.getColumnIndex(MESSAGE_PID);
        int columnTemp = c.getColumnIndex(MESSAGE_TEMP);
        int columnUid = c.getColumnIndex(MESSAGE_UID);
        int columnIsRead = c.getColumnIndex(MESSAGE_IS_READ);

        message.setId(c.getInt(columnId));
        message.setTitle(c.getString(columnTitle));
        message.setMid(c.getInt(columnMid));
        message.setMname(c.getString(columnMname));
        message.setPid(c.getInt(columnPid));
        message.setTemp(c.getInt(columnTemp));
        message.setUid(c.getInt(columnUid));
        message.setIsRead(c.getInt(columnIsRead));

        return message;
    }

    @Override
    public ContentValues deconstruct(PostMessage message) {

        ContentValues values = new ContentValues();
        values.put(MESSAGE_ID, message.getId());
        values.put(MESSAGE_MID, message.getMid());
        values.put(MESSAGE_MNAME, message.getMname());
        values.put(MESSAGE_PID, message.getPid());
        values.put(MESSAGE_TEMP, message.getTemp());
        values.put(MESSAGE_TITLE, message.getTitle());
        values.put(MESSAGE_UID, message.getUid());
        values.put(MESSAGE_IS_READ, message.getIsRead());
        return values;
    }

}
