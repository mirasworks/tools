package com.mirasworks.tools.structures.graph.weighted;

import java.util.Date;

/**
 * @author Koda
 *         represent the link with it's strengh traversal
 *         expressed in percent
 *         Possible evolution is to compute markov
 */
public class LinkInfo {

	/**
	 * how many percent the link had been added
	 */
	private Float percent;
	private int count = 1;

	private Date creationDate = null;
	private Date lastUsedDate = null;

	public LinkInfo() {
		creationDate = new Date();
		lastUsedDate = new Date();
	}

	public Float getPercent() {
		return percent;
	}

	public void setPercent(Float percent) {
		this.percent = percent;
	}

	public int getCount() {
		return count;
	}

	public void incrementCount() {
		count++;
	}

	public void updateUsedDate() {
		lastUsedDate = new Date();		
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getLastUsedDate() {
		return lastUsedDate;
	}

}
