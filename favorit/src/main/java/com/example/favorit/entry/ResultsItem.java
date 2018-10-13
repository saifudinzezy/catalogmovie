package com.example.favorit.entry;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static com.example.favorit.db.DatabaseContract.getColumnInt;
import static com.example.favorit.db.DatabaseContract.getColumnString;
import static com.example.favorit.db.DatabaseContract.movieColumns.OVERVIEW;
import static com.example.favorit.db.DatabaseContract.movieColumns.POSTER_PATH;
import static com.example.favorit.db.DatabaseContract.movieColumns.RELEASE_DATE;
import static com.example.favorit.db.DatabaseContract.movieColumns.TITLE;
import static com.example.favorit.db.DatabaseContract.movieColumns.VOTE_AVERAGE;
import static com.example.favorit.db.DatabaseContract.movieColumns.VOTE_COUNT;

//TODO buat model dari tabel untuk pengambilan nilai / copy model dr project akar
public class ResultsItem implements Parcelable {

	@SerializedName("overview")
	private String overview;

	@SerializedName("original_language")
	private String originalLanguage;

	@SerializedName("original_title")
	private String originalTitle;

	@SerializedName("video")
	private boolean video;

	@SerializedName("title")
	private String title;

	@SerializedName("genre_ids")
	private List<Integer> genreIds;

	@SerializedName("poster_path")
	private String posterPath;

	@SerializedName("backdrop_path")
	private String backdropPath;

	@SerializedName("release_date")
	private String releaseDate;

	@SerializedName("vote_average")
	private double voteAverage;

	@SerializedName("popularity")
	private double popularity;

	@SerializedName("id")
	private int id;

	@SerializedName("adult")
	private boolean adult;

	@SerializedName("vote_count")
	private int voteCount;

	public void setOverview(String overview){
		this.overview = overview;
	}

	public String getOverview(){
		return overview;
	}

	public void setOriginalLanguage(String originalLanguage){
		this.originalLanguage = originalLanguage;
	}

	public String getOriginalLanguage(){
		return originalLanguage;
	}

	public void setOriginalTitle(String originalTitle){
		this.originalTitle = originalTitle;
	}

	public String getOriginalTitle(){
		return originalTitle;
	}

	public void setVideo(boolean video){
		this.video = video;
	}

	public boolean isVideo(){
		return video;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setGenreIds(List<Integer> genreIds){
		this.genreIds = genreIds;
	}

	public List<Integer> getGenreIds(){
		return genreIds;
	}

	public void setPosterPath(String posterPath){
		this.posterPath = posterPath;
	}

	public String getPosterPath(){
		return posterPath;
	}

	public void setBackdropPath(String backdropPath){
		this.backdropPath = backdropPath;
	}

	public String getBackdropPath(){
		return backdropPath;
	}

	public void setReleaseDate(String releaseDate){
		this.releaseDate = releaseDate;
	}

	public String getReleaseDate(){
		return releaseDate;
	}

	public void setVoteAverage(double voteAverage){
		this.voteAverage = voteAverage;
	}

	public double getVoteAverage(){
		return voteAverage;
	}

	public void setPopularity(double popularity){
		this.popularity = popularity;
	}

	public double getPopularity(){
		return popularity;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setAdult(boolean adult){
		this.adult = adult;
	}

	public boolean isAdult(){
		return adult;
	}

	public void setVoteCount(int voteCount){
		this.voteCount = voteCount;
	}

	public int getVoteCount(){
		return voteCount;
	}

	@Override
 	public String toString(){
		return 
			"ResultsItem{" + 
			"overview = '" + overview + '\'' + 
			",original_language = '" + originalLanguage + '\'' + 
			",original_title = '" + originalTitle + '\'' + 
			",video = '" + video + '\'' + 
			",title = '" + title + '\'' + 
			",genre_ids = '" + genreIds + '\'' + 
			",poster_path = '" + posterPath + '\'' + 
			",backdrop_path = '" + backdropPath + '\'' + 
			",release_date = '" + releaseDate + '\'' + 
			",vote_average = '" + voteAverage + '\'' + 
			",popularity = '" + popularity + '\'' + 
			",id = '" + id + '\'' + 
			",adult = '" + adult + '\'' + 
			",vote_count = '" + voteCount + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.overview);
		dest.writeString(this.originalLanguage);
		dest.writeString(this.originalTitle);
		dest.writeByte(this.video ? (byte) 1 : (byte) 0);
		dest.writeString(this.title);
		dest.writeList(this.genreIds);
		dest.writeString(this.posterPath);
		dest.writeString(this.backdropPath);
		dest.writeString(this.releaseDate);
		dest.writeDouble(this.voteAverage);
		dest.writeDouble(this.popularity);
		dest.writeInt(this.id);
		dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
		dest.writeInt(this.voteCount);
	}

	public ResultsItem() {
	}

	protected ResultsItem(Parcel in) {
		this.overview = in.readString();
		this.originalLanguage = in.readString();
		this.originalTitle = in.readString();
		this.video = in.readByte() != 0;
		this.title = in.readString();
		this.genreIds = new ArrayList<Integer>();
		in.readList(this.genreIds, Integer.class.getClassLoader());
		this.posterPath = in.readString();
		this.backdropPath = in.readString();
		this.releaseDate = in.readString();
		this.voteAverage = in.readDouble();
		this.popularity = in.readDouble();
		this.id = in.readInt();
		this.adult = in.readByte() != 0;
		this.voteCount = in.readInt();
	}

	//cursor
	public ResultsItem(Cursor cursor){
		this.id = getColumnInt(cursor, _ID); //id
		this.title = getColumnString(cursor, TITLE);
		this.voteCount = Integer.parseInt(getColumnString(cursor, VOTE_COUNT));
		this.voteAverage = Double.parseDouble(getColumnString(cursor, VOTE_AVERAGE)); //3
		this.posterPath = getColumnString(cursor, POSTER_PATH);
		this.releaseDate = getColumnString(cursor, RELEASE_DATE);
		this.overview = getColumnString(cursor, OVERVIEW); //3
	}

	public static final Creator<ResultsItem> CREATOR = new Creator<ResultsItem>() {
		@Override
		public ResultsItem createFromParcel(Parcel source) {
			return new ResultsItem(source);
		}

		@Override
		public ResultsItem[] newArray(int size) {
			return new ResultsItem[size];
		}
	};
}