package com.example.androidconnexus;

import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;
import com.google.common.base.Joiner;

@Entity
public class Stream implements Comparable<Stream> {

	// id is set by the datastore for us
	@Id
	public Long id;
	public String name;
	public String tags;
	public Date createDate;
	public String coverImageUrl;
  
	// TODO: figure out why this is needed
	@SuppressWarnings("unused")
	private Stream() {
	}
	
	@Override
	public String toString() {
		Joiner joiner = Joiner.on(":");
		return joiner.join(id.toString(), name, tags, createDate.toString());
 	}

	public Stream(String name, String tags, String coverImageUrl) {
		this.name = name;
		this.tags = tags;
		this.coverImageUrl = coverImageUrl;
		this.createDate = new Date();
	}

	@Override
	public int compareTo(Stream other) {
		if (createDate.after(other.createDate)) {
			return 1;
		} else if (createDate.before(other.createDate)) {
			return -1;
		}
		return 0;
	}
}