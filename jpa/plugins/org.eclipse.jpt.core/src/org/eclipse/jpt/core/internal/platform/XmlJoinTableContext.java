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
import org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingInternal;
import org.eclipse.jpt.core.internal.mappings.IJoinColumn;
import org.eclipse.jpt.core.internal.mappings.IJoinTable;
import org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class XmlJoinTableContext extends JoinTableContext {
	
	public XmlJoinTableContext(ParentContext parentContext, IJoinTable table) {
		super(parentContext, table);
	}

	protected JoinColumnContext buildJoinColumnContext(IJoinColumn joinColumn) {
		return new XmlJoinColumnContext(buildJoinColumnParentContext(), joinColumn);
	}
	
	protected JoinColumnContext buildInverseJoinColumnContext(IJoinColumn joinColumn) {
		return new XmlJoinColumnContext(buildInverseJoinColumnParentContext(), joinColumn);
	}
	
	protected XmlJoinColumnContext.ParentContext buildJoinColumnParentContext() {
		return new XmlJoinColumnContext.ParentContext() {
			public void refreshDefaults(DefaultsContext defaults, IProgressMonitor monitor) {
				XmlJoinTableContext.this.refreshDefaults(defaults, monitor);
			}
		
			public IJpaPlatform getPlatform() {
				return XmlJoinTableContext.this.getPlatform();
			}
		
			public IContext getParentContext() {
				return XmlJoinTableContext.this.getParentContext();
			}
		
			public void addToMessages(List<IMessage> messages) {
				XmlJoinTableContext.this.addToMessages(messages);
			}
		
			public IJoinColumn javaJoinColumn(int index) {
				//if the mapping is specified in the xml, then nothing specified in java should be used
				if (relationshipMapping().isVirtual()) {
					return javaRelationshipMapping().getJoinTable().getJoinColumns().get(index);
				}
				return null;
			}
		};
	}
	
	protected XmlJoinColumnContext.ParentContext buildInverseJoinColumnParentContext() {
		return new XmlJoinColumnContext.ParentContext() {
			public void refreshDefaults(DefaultsContext defaults, IProgressMonitor monitor) {
				XmlJoinTableContext.this.refreshDefaults(defaults, monitor);
			}
		
			public IJpaPlatform getPlatform() {
				return XmlJoinTableContext.this.getPlatform();
			}
		
			public IContext getParentContext() {
				return XmlJoinTableContext.this.getParentContext();
			}
		
			public void addToMessages(List<IMessage> messages) {
				XmlJoinTableContext.this.addToMessages(messages);
			}
		
			public IJoinColumn javaJoinColumn(int index) {
				//if the mapping is specified in the xml, then nothing specified in java should be used
				if (relationshipMapping().isVirtual()) {
					return javaRelationshipMapping().getJoinTable().getInverseJoinColumns().get(index);
				}
				return null;
			}
		};
	}
	
	protected String joinTableDefaultName(DefaultsContext defaultsContext) {
		XmlMultiRelationshipMappingInternal xmlMultiRelationshipMapping = relationshipMapping();
		if (xmlMultiRelationshipMapping.isVirtual()) {
			if (!xmlMultiRelationshipMapping.typeMapping().isXmlMetadataComplete()) {
				IMultiRelationshipMapping javaRelationshipMapping = javaRelationshipMapping();
				if (javaRelationshipMapping != null) {
					return javaRelationshipMapping.getJoinTable().getName();
				}
			}
		}
		return super.joinTableDefaultName(defaultsContext);
	}

	@Override
	protected XmlMultiRelationshipMappingInternal relationshipMapping() {
		return (XmlMultiRelationshipMappingInternal) super.relationshipMapping();
	}
	
	private IMultiRelationshipMapping javaRelationshipMapping() {
		return getParentContext().javaRelationshipMapping();
	}
	
	@Override
	public ParentContext getParentContext() {
		return (ParentContext) super.getParentContext();
	}
	
	public interface ParentContext extends IContext {
		/**
		 * Return the JavaAttributeOverride that corresponds to the xml attribute override
		 * with the given name.  Return null if it does not exist
		 */
		IMultiRelationshipMapping javaRelationshipMapping();
	}
	

}
