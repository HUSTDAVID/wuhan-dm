
package com.wh.dm.db;

import com.wh.dm.type.ArticleMagzine;

import android.content.ContentValues;
import android.database.Cursor;

public class ArticleMagzineBuilder extends DatabaseBuilder<ArticleMagzine> {

    private static final String ArticleMagzine_Sid = "sid";
    private static final String ArticleMagzine_Id = "id";
    private static final String ArticleMagzine_Title = "title";
    private static final String ArticleMagzine_Source = "source";
    private static final String ArticleMagzine_Writer = "writer";
    private static final String ArticleMagzine_Author = "author";
    private static final String ArticleMagzine_Litpic = "litpic";
    private static final String ArticleMagzine_Pubdate = "pubdate";
    private static final String ArticleMagzine_Body = "body";

    @Override
    public ArticleMagzine build(Cursor c) {

        int ColumnSid = c.getColumnIndex(ArticleMagzine_Sid);
        int ColumnId = c.getColumnIndex(ArticleMagzine_Id);
        int ColumnTitle = c.getColumnIndex(ArticleMagzine_Title);
        int ColumnAuthor = c.getColumnIndex(ArticleMagzine_Author);
        int ColumnWriter = c.getColumnIndex(ArticleMagzine_Writer);
        int ColumnSource = c.getColumnIndex(ArticleMagzine_Source);
        int ColumnLitpic = c.getColumnIndex(ArticleMagzine_Litpic);
        int ColumnPubdate = c.getColumnIndex(ArticleMagzine_Pubdate);
        int ColumnBody = c.getColumnIndex(ArticleMagzine_Body);

        ArticleMagzine articleMagzine = new ArticleMagzine();

        articleMagzine.setSid(c.getInt(ColumnSid));
        articleMagzine.setAuthor(c.getString(ColumnAuthor));
        articleMagzine.setWriter(c.getString(ColumnWriter));
        articleMagzine.setId(c.getInt(ColumnId));
        articleMagzine.setTitle(c.getString(ColumnTitle));
        articleMagzine.setSource(c.getString(ColumnSource));
        articleMagzine.setLitpic(c.getString(ColumnLitpic));
        articleMagzine.setPubdate(c.getString(ColumnPubdate));
        articleMagzine.setBody(c.getString(ColumnBody));

        return articleMagzine;
    }

    @Override
    public ContentValues deconstruct(ArticleMagzine articleMagzine) {

        ContentValues values = new ContentValues();
        values.put(ArticleMagzine_Sid, articleMagzine.getSid());
        values.put(ArticleMagzine_Id, articleMagzine.getId());
        values.put(ArticleMagzine_Title, articleMagzine.getTitle());
        values.put(ArticleMagzine_Source, articleMagzine.getSource());
        values.put(ArticleMagzine_Author, articleMagzine.getAuthor());
        values.put(ArticleMagzine_Writer, articleMagzine.getWriter());
        values.put(ArticleMagzine_Litpic, articleMagzine.getLitpic());
        values.put(ArticleMagzine_Pubdate, articleMagzine.getPubdate());
        values.put(ArticleMagzine_Body, articleMagzine.getBody());

        return values;
    }

}
