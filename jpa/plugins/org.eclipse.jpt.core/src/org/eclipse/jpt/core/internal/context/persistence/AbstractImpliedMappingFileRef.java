/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.persistence;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.utility.TextRange;

public class AbstractImpliedMappingFileRef
	extends AbstractMappingFileRef
{
	
	// ********** construction/initialization **********

	protected AbstractImpliedMappingFileRef(PersistenceUnit parent, String resourceFileName) {
		super(parent, resourceFileName);
	}
		
	public boolean isImplied() {
		return true;
	}

	public void setFileName(String fileName) {
		throw new UnsupportedOperationException("Cannot set an implied mapping file ref's 'fileName'"); //$NON-NLS-1$
	}

	public TextRange getSelectionTextRange() {
		return null;
	}
	
	public TextRange getValidationTextRange() {
		return this.getPersistenceUnit().getValidationTextRange();
	}
	
	public boolean containsOffset(int textOffset) {
		return false;
	}
	
	public void update(XmlMappingFileRef mappingFileRef) {
		if (mappingFileRef != null) {
			throw new IllegalArgumentException("mappingFileRef should be null for an implied mapping file"); //$NON-NLS-1$
		}
		super.update();
	}

}
