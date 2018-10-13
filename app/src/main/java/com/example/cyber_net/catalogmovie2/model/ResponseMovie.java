package com.example.cyber_net.catalogmovie2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResponseMovie implements Parcelable {

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private List<ResultsItem> results;

	@SerializedName("total_results")
	private int totalResults;

	public void setPage(int page){
		this.page = page;
	}

	public int getPage(){
		return page;
	}

	public void setTotalPages(int totalPages){
		this.totalPages = totalPages;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public void setResults(List<ResultsItem> results){
		this.results = results;
	}

	public List<ResultsItem> getResults(){
		return results;
	}

	public void setTotalResults(int totalResults){
		this.totalResults = totalResults;
	}

	public int getTotalResults(){
		return totalResults;
	}

	@Override
 	public String toString(){
		return 
			"ResponseMovie{" + 
			"page = '" + page + '\'' + 
			",total_pages = '" + totalPages + '\'' + 
			",results = '" + results + '\'' + 
			",total_results = '" + totalResults + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.page);
		dest.writeInt(this.totalPages);
		dest.writeList(this.results);
		dest.writeInt(this.totalResults);
	}

	public ResponseMovie() {
	}

	protected ResponseMovie(Parcel in) {
		this.page = in.readInt();
		this.totalPages = in.readInt();
		this.results = new ArrayList<ResultsItem>();
		in.readList(this.results, ResultsItem.class.getClassLoader());
		this.totalResults = in.readInt();
	}

	public static final Parcelable.Creator<ResponseMovie> CREATOR = new Parcelable.Creator<ResponseMovie>() {
		@Override
		public ResponseMovie createFromParcel(Parcel source) {
			return new ResponseMovie(source);
		}

		@Override
		public ResponseMovie[] newArray(int size) {
			return new ResponseMovie[size];
		}
	};
}