/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.mappings.ISecondaryTable;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class XmlSecondaryTableContext extends SecondaryTableContext
{
	public XmlSecondaryTableContext(ParentContext parentContext, ISecondaryTable secondaryTable) {
		super(parentContext, secondaryTable);
	}
	
	@Override
	protected PrimaryKeyJoinColumnContext buildPrimaryKeyJoinColumnContext(IPrimaryKeyJoinColumn pkJoinColumn) {
		return new XmlPrimaryKeyJoinColumnContext(buildJoinColumnParentContext(), pkJoinColumn);
	}
	
	protected XmlPrimaryKeyJoinColumnContext.ParentContext buildJoinColumnParentContext() {
		return new XmlPrimaryKeyJoinColumnContext.ParentContext() {
			public void refreshDefaults(DefaultsContext defaults, IProgressMonitor monitor) {
				XmlSecondaryTableContext.this.refreshDefaults(defaults, monitor);
			}
		
			public IJpaPlatform getPlatform() {
				return XmlSecondaryTableContext.this.getPlatform();
			}
		
			public IContext getParentContext() {
				return XmlSecondaryTableContext.this.getParentContext();
			}
		
			public void addToMessages(List<IMessage> messages) {
				XmlSecondaryTableContext.this.addToMessages(messages);
			}
		
			public IPrimaryKeyJoinColumn javaPrimaryKeyJoinColumn(int index) {
				if (getSecondaryTable().isVirtual()) {
					return javaSecondaryTable(index).getSpecifiedPrimaryKeyJoinColumns().get(index);
				}
				return null;
			}
		};
	}

	@Override
	public ParentContext getParentContext() {
		return (ParentContext) super.getParentContext();
	}
	
	private ISecondaryTable javaSecondaryTable(int index) {
		return getParentContext().javaSecondaryTable(index);
	}
	
	public interface ParentContext extends IContext {
		/**
		 * Return the JavaSecondaryTable at the given index.  Return null if it does not exist
		 */
		ISecondaryTable javaSecondaryTable(int index);
	}

}
