/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.oxm;

import org.eclipse.jpt.common.utility.internal.ClassNameTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jaxb.core.context.TypeName;

public class OxmTypeName
		implements TypeName {
	
	// never null, maybe empty
	protected String fullyQualifiedName;
	
	public OxmTypeName(String fullyQualifiedName) {
		assert (fullyQualifiedName != null);
		this.fullyQualifiedName = fullyQualifiedName;
	}
	
	public String getPackageName() {
		if (StringTools.isBlank(this.fullyQualifiedName)) {
			return StringTools.EMPTY_STRING;
		}
		return ClassNameTools.packageName(this.fullyQualifiedName);
	}
	
	public String getSimpleName() {
		if (StringTools.isBlank(this.fullyQualifiedName)) {
			return StringTools.EMPTY_STRING;
		}
		return ClassNameTools.simpleName(this.fullyQualifiedName);
	}
	
	public String getTypeQualifiedName() {
		if (StringTools.isBlank(this.fullyQualifiedName)) {
			return StringTools.EMPTY_STRING;
		}
		String packageName = this.getPackageName();
		return (StringTools.isBlank(packageName)) ? this.fullyQualifiedName : this.fullyQualifiedName.substring(packageName.length() + 1);
	}
	
	public String getFullyQualifiedName() {
		return this.fullyQualifiedName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		return ObjectTools.equals(this.fullyQualifiedName, ((OxmTypeName) obj).fullyQualifiedName);
	}
	
	@Override
	public int hashCode() {
		return ObjectTools.hashCode(this.fullyQualifiedName);
	}
}