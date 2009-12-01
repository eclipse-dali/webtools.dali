/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOrderColumn2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOrderable2_0;
import org.eclipse.jpt.core.jpa2.resource.java.OrderColumn2_0Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.OrderByAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class GenericJavaOrderable
	extends AbstractJavaJpaContextNode
	implements JavaOrderable2_0
{
	protected String specifiedOrderBy = null;
	protected boolean noOrdering = false;
	protected boolean pkOrdering = false;
	protected boolean customOrdering = false;
	
	protected boolean orderColumnOrdering = false;
	protected final JavaOrderColumn2_0 orderColumn;

	protected String specifiedMapKey;
	protected boolean noMapKey = false;
	protected boolean pkMapKey = false;
	protected boolean customMapKey = false;


	public GenericJavaOrderable(JavaAttributeMapping parent) {
		super(parent);
		this.orderColumn = ((JpaFactory2_0) getJpaFactory()).buildJavaOrderColumn(this, this);
	}

	public void initialize() {
		this.initializeOrdering();
	}

	public void update() {
		this.updateOrdering();
	}

	@Override
	public JavaAttributeMapping getParent() {
		return (JavaAttributeMapping) super.getParent();
	}
	
	protected JavaPersistentAttribute getPersistentAttribute() {
		return getParent().getPersistentAttribute();
	}
	
	protected JavaResourcePersistentAttribute getResourcePersistentAttribute() {
		return this.getPersistentAttribute().getResourcePersistentAttribute();
	}

	
	// ********** JavaBaseColumn.Owner implementation **********  

	public Table getDbTable(String tableName) {
		return this.getTypeMapping().getDbTable(tableName);
	}

	public String getDefaultColumnName() {
		return getPersistentAttribute().getName() + "_ORDER"; //$NON-NLS-1$
	}

	public TypeMapping getTypeMapping() {
		return getPersistentAttribute().getTypeMapping();
	}
	
	// ********** order by **********  

	public String getSpecifiedOrderBy() {
		return this.specifiedOrderBy;
	}

	public void setSpecifiedOrderBy(String orderBy) {
		String old = this.specifiedOrderBy;
		this.specifiedOrderBy = orderBy;
		OrderByAnnotation orderByAnnotation = this.getOrderByAnnotation();
		if (orderBy == null) {
			if (orderByAnnotation != null) { 
				this.removeOrderByAnnotation();
			}
		} else {
			if (orderByAnnotation == null) {
				orderByAnnotation = this.addOrderByAnnotation();
			}
			orderByAnnotation.setValue(orderBy);
		}
		this.firePropertyChanged(SPECIFIED_ORDER_BY_PROPERTY, old, orderBy);
	}

	protected void setSpecifiedOrderBy_(String orderBy) {
		String old = this.specifiedOrderBy;
		this.specifiedOrderBy = orderBy;
		this.firePropertyChanged(SPECIFIED_ORDER_BY_PROPERTY, old, orderBy);
	}

	protected void initializeOrdering() {
		OrderByAnnotation orderByAnnotation = this.getOrderByAnnotation();
		OrderColumn2_0Annotation orderColumnAnnotation = this.getOrderColumnAnnotation();
		if (orderByAnnotation == null && orderColumnAnnotation == null) {
			this.noOrdering = true;
		} else if (orderByAnnotation != null){
			this.specifiedOrderBy = orderByAnnotation.getValue();
			if (this.specifiedOrderBy == null) {
				this.pkOrdering = true;
			} else {
				this.customOrdering = true;
			}
		} else {
			this.orderColumnOrdering = true;
			this.orderColumn.initialize(orderColumnAnnotation);
		}
	}

	protected void updateOrdering() {
		OrderByAnnotation orderByAnnotation = this.getOrderByAnnotation();
		OrderColumn2_0Annotation orderColumnAnnotation = this.getOrderColumnAnnotation();
		if (orderByAnnotation == null && orderColumnAnnotation == null) {
			this.setSpecifiedOrderBy_(null);
			this.setNoOrdering_(true);
			this.setPkOrdering_(false);
			this.setCustomOrdering_(false);
			this.setOrderColumnOrdering_(false);
		} else if (orderByAnnotation != null) {
			String ob = orderByAnnotation.getValue();
			this.setSpecifiedOrderBy_(ob);
			this.setNoOrdering_(false);
			this.setPkOrdering_(ob == null);
			this.setCustomOrdering_(ob != null);
			this.setOrderColumnOrdering_(false);
		} else {
			this.setSpecifiedOrderBy_(null);
			this.setNoOrdering_(false);
			this.setPkOrdering_(false);
			this.setCustomOrdering_(false);
			this.setOrderColumnOrdering_(true);
			this.orderColumn.update(orderColumnAnnotation);
		}
	}

	protected OrderByAnnotation getOrderByAnnotation() {
		return (OrderByAnnotation) this.getResourcePersistentAttribute().getAnnotation(OrderByAnnotation.ANNOTATION_NAME);
	}

	protected OrderByAnnotation addOrderByAnnotation() {
		return (OrderByAnnotation) this.getResourcePersistentAttribute().addAnnotation(OrderByAnnotation.ANNOTATION_NAME);
	}

	protected void removeOrderByAnnotation() {
		this.getResourcePersistentAttribute().removeAnnotation(OrderByAnnotation.ANNOTATION_NAME);
	}


	// ********** no ordering **********  

	public boolean isNoOrdering() {
		return this.noOrdering;
	}

	public void setNoOrdering(boolean noOrdering) {
		boolean old = this.noOrdering;
		this.noOrdering = noOrdering;
		if (noOrdering) {
			if (this.getOrderByAnnotation() != null) {
				this.removeOrderByAnnotation();
			}
			if (this.getOrderColumnAnnotation() != null) {
				this.removeOrderColumnAnnotation();
			}
		} else {
			// the 'noOrdering' flag is cleared as a
			// side-effect of setting the other flags,
			// via a call to #setNoOrdering_(boolean)
		}
		this.firePropertyChanged(NO_ORDERING_PROPERTY, old, noOrdering);
	}

	protected void setNoOrdering_(boolean noOrdering) {
		boolean old = this.noOrdering;
		this.noOrdering = noOrdering;
		this.firePropertyChanged(NO_ORDERING_PROPERTY, old, noOrdering);	
	}


	// ********** pk ordering **********  

	public boolean isPkOrdering() {
		return this.pkOrdering;
	}

	public void setPkOrdering(boolean pkOrdering) {
		boolean old = this.pkOrdering;
		this.pkOrdering = pkOrdering;
		OrderByAnnotation orderByAnnotation = this.getOrderByAnnotation();
		if (pkOrdering) {
			if (orderByAnnotation == null) {
				this.addOrderByAnnotation();
			} else {
				orderByAnnotation.setValue(null);
			}
		} else {
			// the 'pkOrdering' flag is cleared as a
			// side-effect of setting the other flags,
			// via a call to #setPkOrdering_(boolean)
		}
		this.firePropertyChanged(PK_ORDERING_PROPERTY, old, pkOrdering);
	}

	protected void setPkOrdering_(boolean pkOrdering) {
		boolean old = this.pkOrdering;
		this.pkOrdering = pkOrdering;
		this.firePropertyChanged(PK_ORDERING_PROPERTY, old, pkOrdering);
	}


	// ********** custom ordering **********  

	public boolean isCustomOrdering() {
		return this.customOrdering;
	}

	public void setCustomOrdering(boolean customOrdering) {
		boolean old = this.customOrdering;
		this.customOrdering = customOrdering;
		if (customOrdering) {
			this.setSpecifiedOrderBy(""); //$NON-NLS-1$
		} else {
			// the 'customOrdering' flag is cleared as a
			// side-effect of setting the other flags,
			// via a call to #setCustomOrdering_(boolean)
		}
		this.firePropertyChanged(CUSTOM_ORDERING_PROPERTY, old, customOrdering);
	}

	protected void setCustomOrdering_(boolean customOrdering) {
		boolean old = this.customOrdering;
		this.customOrdering = customOrdering;
		this.firePropertyChanged(CUSTOM_ORDERING_PROPERTY, old, customOrdering);
	}

	// ********** order column 2.0 **********  

	public boolean isOrderColumnOrdering() {
		return this.orderColumnOrdering;
	}
	
	public void setOrderColumnOrdering(boolean orderColumnOrdering) {
		boolean old = this.orderColumnOrdering;
		this.orderColumnOrdering = orderColumnOrdering;
		OrderColumn2_0Annotation orderColumnAnnotation = this.getOrderColumnAnnotation();
		if (orderColumnOrdering) {
			if (orderColumnAnnotation == null) {
				this.addOrderColumnAnnotation();
			}
			this.removeOrderByAnnotation();
		} else {
			removeOrderColumnAnnotation();
		}
		this.firePropertyChanged(ORDER_COLUMN_ORDERING_PROPERTY, old, orderColumnOrdering);
	}
	
	protected void setOrderColumnOrdering_(boolean orderColumnOrdering) {
		boolean old = this.orderColumnOrdering;
		this.orderColumnOrdering = orderColumnOrdering;
		this.firePropertyChanged(ORDER_COLUMN_ORDERING_PROPERTY, old, orderColumnOrdering);
	}
	
	public JavaOrderColumn2_0 getOrderColumn() {
		return this.orderColumn;
	}
	
	protected OrderColumn2_0Annotation getOrderColumnAnnotation() {
		return (OrderColumn2_0Annotation) this.getResourcePersistentAttribute().getAnnotation(OrderColumn2_0Annotation.ANNOTATION_NAME);
	}

	protected OrderColumn2_0Annotation addOrderColumnAnnotation() {
		return (OrderColumn2_0Annotation) this.getResourcePersistentAttribute().addAnnotation(OrderColumn2_0Annotation.ANNOTATION_NAME);
	}

	protected void removeOrderColumnAnnotation() {
		this.getResourcePersistentAttribute().removeAnnotation(OrderColumn2_0Annotation.ANNOTATION_NAME);
	}
	
	
	// ********** Java completion proposals **********  

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.getOrderColumn().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return null;
	}


	// ********** validation **********

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.getOrderByAnnotationTextRange(astRoot);
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange(astRoot);
	}

	protected TextRange getOrderByAnnotationTextRange(CompilationUnit astRoot) {
		OrderByAnnotation orderByAnnotation = this.getOrderByAnnotation();
		return (orderByAnnotation == null) ? null : orderByAnnotation.getTextRange(astRoot);
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		if (getOrderColumnAnnotation() != null && getOrderByAnnotation() != null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.ORDER_COLUMN_AND_ORDER_BY_BOTH_SPECIFIED,
					new String[] {getPersistentAttribute().getName()},
					this.getParent(),
					this.getOrderByAnnotationTextRange(astRoot)
				)
			);
		}
		if (isOrderColumnOrdering()) {
			//TODO validation message if type is not List
		}
	}
	
}
