/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.OrderBy;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.OrderColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.GenericJavaOrderColumn2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.OrderColumnAnnotationDefinition2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.SpecifiedOrderColumn2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaOrderable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaSpecifiedOrderColumn2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.OrderColumnAnnotation2_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.OrderByAnnotation;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java ordering
 * <p>
 * <strong>(JPA 2.0 only) NB:</strong> If both the "order by" and the "order
 * column" annotations are present (which is prohibited by the JPA spec),
 * order by is ignored (although the model is still there)
 */
public class GenericJavaOrderable
		extends AbstractJavaContextModel<JavaAttributeMapping>
		implements JavaOrderable2_0 {
	
	protected final JavaOrderable2_0.ParentAdapter parentAdapter;
	
	protected boolean noOrdering = false;
	
	protected boolean orderByOrdering = false;
	// never null
	protected OrderBy orderBy;
	
	protected boolean orderColumnOrdering = false;
	// never null
	protected JavaSpecifiedOrderColumn2_0 orderColumn;
	// JPA 1.0
	protected OrderColumnAnnotation2_0 nullOrderColumnAnnotation;
	
	
	/**
	 * JPA 1.0
	 */
	public GenericJavaOrderable(JavaAttributeMapping parent) {
		this(new JavaOrderable2_0.ParentAdapter.Null(parent));
	}
	
	/**
	 * JPA 2.0
	 */
	public GenericJavaOrderable(JavaOrderable2_0.ParentAdapter parentAdapter) {
		super(parentAdapter.getOrderableParent());
		
		this.parentAdapter = parentAdapter;
		
		initNoOrdering();
		initOrderBy();
		initOrderColumn();
	}
	
	
	// ********** synchronize/update **********
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncNoOrdering();
		syncOrderBy();
		syncOrderColumn();
	}

	@Override
	public void update() {
		super.update();
		updateOrderBy();
		updateOrderColumn();
	}
	
	
	// ****** no ordering *****
	
	public boolean isNoOrdering() {
		return this.noOrdering;
	}
	
	public void setNoOrdering() {
		removeOrderColumnAnnotation();
		removeOrderByAnnotation();
		
		setNoOrdering_(true);
		this.orderBy.setKey(null);
		setOrderByOrdering_(false);
		setOrderColumnOrdering_(false);
	}
	
	protected void setNoOrdering_(boolean noOrdering) {
		boolean old = this.noOrdering;
		this.noOrdering = noOrdering;
		this.firePropertyChanged(NO_ORDERING_PROPERTY, old, noOrdering);
	}
	
	protected void initNoOrdering() {
		this.noOrdering = buildNoOrdering();
	}
	
	protected void syncNoOrdering() {
		setNoOrdering_(buildNoOrdering());
	}
	
	protected boolean buildNoOrdering() {
		return isJpa2_0Compatible() ? buildNoOrdering2_0() : buildNoOrdering1_0();
	}
	
	/**
	 * both annotations are missing
	 */
	protected boolean buildNoOrdering2_0() {
		return getOrderByAnnotation() == null && getOrderColumnAnnotation() == null;
	}
	
	/**
	 * the order-by annotation is missing
	 */
	protected boolean buildNoOrdering1_0() {
		return getOrderByAnnotation() == null;
	}
	
	
	// ***** order-by ordering *****
	
	public boolean isOrderByOrdering() {
		return this.orderByOrdering;
	}
	
	public void setOrderByOrdering() {
		removeOrderColumnAnnotation();
		if (getOrderByAnnotation() == null) {
			addOrderByAnnotation();
		}
		
		setNoOrdering_(false);
		setOrderByOrdering_(true);
		setOrderColumnOrdering_(false);
	}
	
	protected void setOrderByOrdering_(boolean orderByOrdering) {
		boolean old = this.orderByOrdering;
		this.orderByOrdering = orderByOrdering;
		firePropertyChanged(ORDER_BY_ORDERING_PROPERTY, old, orderByOrdering);
	}
	
	public OrderBy getOrderBy() {
		return this.orderBy;
	}
	
	protected void initOrderBy() {
		this.orderByOrdering = buildOrderByOrdering();
		this.orderBy = buildOrderBy();
	}
	
	protected void syncOrderBy() {
		setOrderByOrdering_(buildOrderByOrdering());
		this.orderBy.synchronizeWithResourceModel();
	}
	
	protected void updateOrderBy() {
		this.orderBy.update();
	}
	
	protected boolean buildOrderByOrdering() {
		return isJpa2_0Compatible() ? buildOrderByOrdering2_0() : buildOrderByOrdering1_0();
	}
	
	/**
	 * OrderBy annotation is present, but OrderColumn is absent
	 */
	protected boolean buildOrderByOrdering2_0() {
		return getOrderByAnnotation() != null && getOrderColumnAnnotation() == null;
	}
	
	/**
	 * OrderBy annotation is present
	 */
	protected boolean buildOrderByOrdering1_0() {
		return getOrderByAnnotation() != null;
	}
	
	protected OrderBy buildOrderBy() {
		return new GenericJavaOrderBy(this, new OrderByContext());
	}
	
	protected class OrderByContext
			implements GenericJavaOrderBy.Context {
		
		public OrderByAnnotation getAnnotation(boolean addIfAbsent) {
			OrderByAnnotation annotation = GenericJavaOrderable.this.getOrderByAnnotation();
			if (annotation == null && addIfAbsent) {
				annotation = addOrderByAnnotation();
			}
			return annotation;
		}
	}
	
	
	// ***** order by annotation *****
	
	protected OrderByAnnotation getOrderByAnnotation() {
		return (OrderByAnnotation) this.getResourceAttribute().getAnnotation(JPA.ORDER_BY);
	}
	
	protected OrderByAnnotation addOrderByAnnotation() {
		return (OrderByAnnotation) this.getResourceAttribute().addAnnotation(JPA.ORDER_BY);
	}
	
	protected void removeOrderByAnnotation() {
		if (getResourceAttribute().getAnnotation(JPA.ORDER_BY) != null) { 
			getResourceAttribute().removeAnnotation(JPA.ORDER_BY);
		}
	}
	
	
	// ***** order column ordering *****
	
	public boolean isOrderColumnOrdering() {
		return this.orderColumnOrdering;
	}
	
	public void setOrderColumnOrdering() {
		removeOrderByAnnotation();
		if (getOrderColumnAnnotation() == null) {
			addOrderColumnAnnotation();
		}
		
		setNoOrdering_(false);
		setOrderByOrdering_(false);
		this.orderBy.setKey(null);
		setOrderColumnOrdering_(true);
	}

	protected void setOrderColumnOrdering_(boolean orderColumnOrdering) {
		boolean old = this.orderColumnOrdering;
		this.orderColumnOrdering = orderColumnOrdering;
		this.firePropertyChanged(ORDER_COLUMN_ORDERING_PROPERTY, old, orderColumnOrdering);
	}
	
	public JavaSpecifiedOrderColumn2_0 getOrderColumn() {
		return this.orderColumn;
	}
	
	protected void initOrderColumn() {
		this.orderColumnOrdering = buildOrderColumnOrdering();
		this.orderColumn = buildOrderColumn();
	}
	
	protected void syncOrderColumn() {
		setOrderColumnOrdering_(buildOrderColumnOrdering());
		this.orderColumn.synchronizeWithResourceModel();
	}
	
	protected void updateOrderColumn() {
		this.orderColumn.update();
	}
	
	/**
	 * Only true if JPA 2.0 *and* annotation is present
	 */
	protected boolean buildOrderColumnOrdering() {
		return isJpa2_0Compatible() ? getOrderColumnAnnotation() != null : false;
	}
	
	protected JavaSpecifiedOrderColumn2_0 buildOrderColumn() {
		JavaSpecifiedOrderColumn2_0.ParentAdapter columnParentAdapter = new OrderColumnParentAdapter();
		return isJpa2_0Compatible() ?
				getJpaFactory2_0().buildJavaOrderColumn(columnParentAdapter) :
				new GenericJavaOrderColumn2_0(columnParentAdapter);
	}
	
	
	// ***** order column annotation *****
	
	protected OrderColumnAnnotation2_0 getOrderColumnAnnotation() {
		return (OrderColumnAnnotation2_0) this.getResourceAttribute().getAnnotation(OrderColumnAnnotation2_0.ANNOTATION_NAME);
	}
	
	protected OrderColumnAnnotation2_0 addOrderColumnAnnotation() {
		return (OrderColumnAnnotation2_0) this.getResourceAttribute().addAnnotation(OrderColumnAnnotation2_0.ANNOTATION_NAME);
	}
	
	protected void removeOrderColumnAnnotation() {
		if (getResourceAttribute().getAnnotation(OrderColumnAnnotation2_0.ANNOTATION_NAME) != null) {
			getResourceAttribute().removeAnnotation(OrderColumnAnnotation2_0.ANNOTATION_NAME);
		}
	}
	
	/**
	 * If we are in a JPA 1.0 project, return a <em>null</em> annotation.
	 */
	public OrderColumnAnnotation2_0 getNonNullOrderColumnAnnotation() {
		// hmmmm...
		return this.isJpa2_0Compatible() ?
				(OrderColumnAnnotation2_0) this.getResourceAttribute().getNonNullAnnotation(OrderColumnAnnotation2_0.ANNOTATION_NAME) :
				this.getNullOrderColumnAnnotation();
	}
	
	protected OrderColumnAnnotation2_0 getNullOrderColumnAnnotation() {
		if (this.nullOrderColumnAnnotation == null) {
			this.nullOrderColumnAnnotation = this.buildNullOrderColumnAnnotation();
		}
		return this.nullOrderColumnAnnotation;
	}
	
	protected OrderColumnAnnotation2_0 buildNullOrderColumnAnnotation() {
		// hmmmm...
		return (OrderColumnAnnotation2_0) OrderColumnAnnotationDefinition2_0.instance().buildNullAnnotation(this.getResourceAttribute());
	}
	
	
	// ***** misc *****
	
	protected JavaAttributeMapping getAttributeMapping() {
		return this.parent;
	}
	
	protected JavaSpecifiedPersistentAttribute getPersistentAttribute() {
		return this.getAttributeMapping().getPersistentAttribute();
	}
	
	public JavaResourceAttribute getResourceAttribute() {
		return this.getPersistentAttribute().getResourceAttribute();
	}
	
	// JPA 2.0 only
	public String getDefaultTableName() {
		return this.parentAdapter.getTableName();
	}
	
	// JPA 2.0 only
	protected Table resolveDbTable(String tableName) {
		return this.parentAdapter.resolveDbTable(tableName);
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		
		return this.orderColumn.getCompletionProposals(pos);
	}
	
	
	// ***** validation *****
	
	public TextRange getValidationTextRange() {
		TextRange textRange = this.getOrderByAnnotationTextRange();
		return (textRange != null) ? textRange : this.getAttributeMapping().getValidationTextRange();
	}
	
	protected TextRange getOrderByAnnotationTextRange() {
		OrderByAnnotation orderByAnnotation = this.getOrderByAnnotation();
		return (orderByAnnotation == null) ? null : orderByAnnotation.getTextRange();
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		if ((getOrderColumnAnnotation() != null) && (getOrderByAnnotation() != null)) {
			if (this.getPersistentAttribute().isVirtual()) {
				messages.add(
						this.buildValidationMessage(
							this.getAttributeMapping(),
							this.getPersistentAttribute().getValidationTextRange(),
							JptJpaCoreValidationMessages.ORDER_COLUMN_AND_ORDER_BY_BOTH_SPECIFIED,
							this.getPersistentAttribute().getName()
						)
					);
			}
			else {
				messages.add(
					this.buildValidationMessage(
						this.getAttributeMapping(),
						this.getOrderByAnnotationTextRange(),
						JptJpaCoreValidationMessages.ORDER_COLUMN_AND_ORDER_BY_BOTH_SPECIFIED,
						this.getPersistentAttribute().getName()
					)
				);
			}
		}
		if (this.orderColumnOrdering) {
			//TODO validation message if type is not List
			this.orderColumn.validate(messages, reporter);
		}
	}
	
	
	// ***** order column parent adapter (JPA 2.0) *****
	
	public class OrderColumnParentAdapter
			implements JavaSpecifiedOrderColumn2_0.ParentAdapter {
		
		public JavaOrderable2_0 getColumnParent() {
			return GenericJavaOrderable.this;
		}
		
		public String getDefaultTableName() {
			return GenericJavaOrderable.this.getDefaultTableName();
		}
		
		public Table resolveDbTable(String tableName) {
			return GenericJavaOrderable.this.resolveDbTable(tableName);
		}
		
		public String getDefaultColumnName(NamedColumn column) {
			return this.getPersistentAttribute().getName() + "_ORDER"; //$NON-NLS-1$
		}
		
		public TextRange getValidationTextRange() {
			return GenericJavaOrderable.this.getValidationTextRange();
		}
		
		public JpaValidator buildColumnValidator(NamedColumn column) {
			return new OrderColumnValidator(this.getPersistentAttribute(), (SpecifiedOrderColumn2_0) column);
		}
		
		public OrderColumnAnnotation2_0 getColumnAnnotation() {
			return GenericJavaOrderable.this.getNonNullOrderColumnAnnotation();
		}
		
		public void removeColumnAnnotation() {
			GenericJavaOrderable.this.removeOrderColumnAnnotation();
		}
		
		protected JavaSpecifiedPersistentAttribute getPersistentAttribute() {
			return GenericJavaOrderable.this.getPersistentAttribute();
		}
	}
}
