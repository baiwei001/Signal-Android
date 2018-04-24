package org.thoughtcrime.securesms.mms;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.thoughtcrime.securesms.database.Address;
import org.thoughtcrime.securesms.database.model.MessageRecord;

/**
 * Represents the information required to find the {@link MessageRecord} pointed to by a quote.
 */
public class QuoteId {

  private static final String TAG     = "QuoteModel";
  private static final String ID      = "id";
  private static final String AUTHOR  = "author";

  public final long    id;
  public final Address author;

  public QuoteId(long id, @NonNull Address author) {
    this.id     = id;
    this.author = author;
  }

  @NonNull
  public String serialize() {
    try {
      JSONObject object = new JSONObject();
      object.put(ID, id);
      object.put(AUTHOR, author.serialize());
      return object.toString();
    } catch (JSONException e) {
      Log.e(TAG, "Failed to serialize to json", e);
      return "";
    }
  }

  @Nullable
  public static QuoteId deserialize(@NonNull String serialized) {
    try {
      JSONObject json = new JSONObject(serialized);
      return new QuoteId(json.getLong(ID), Address.fromSerialized(json.getString(AUTHOR)));
    } catch (JSONException e) {
      Log.e(TAG, "Failed to deserialize from json", e);
      return null;
    }
  }
}
