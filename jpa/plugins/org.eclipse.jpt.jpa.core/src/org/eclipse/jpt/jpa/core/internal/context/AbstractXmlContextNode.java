/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context;

import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;

public abstract class AbstractXmlContextNode
	extends AbstractJpaContextNode
	implements XmlContextNode
{
	protected AbstractXmlContextNode(JpaContextNode parent) {
		super(parent);
	}


	// *********** completion proposals ***********
	
	public Iterable<String> getXmlCompletionProposals(int pos) {
		if (this.connectionProfileIsActive()) {
			Iterable<String> result = this.getConnectedXmlCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
	
	/**
	 * This method is called if the database is connected, allowing us to
	 * get candidates from the various database tables etc.
	 * This method should <em>not</em> be cascaded to "child" objects; it should
	 * only return candidates for the current object. The cascading is
	 * handled by {@link #getXmlCompletionProposals(int)}.
	 */
	protected Iterable<String> getConnectedXmlCompletionProposals(int pos) {
		return null;
	}
}
