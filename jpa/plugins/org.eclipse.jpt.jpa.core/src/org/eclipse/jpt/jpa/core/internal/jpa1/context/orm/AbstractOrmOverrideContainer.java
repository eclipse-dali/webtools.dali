/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.List;
import java.util.Vector;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.jpa.core.context.Override_;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyOverride;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.VirtualOverride;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.context.orm.OrmOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualOverride;
import org.eclipse.jpt.jpa.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.OverrideTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.jpa.core.resource.orm.XmlOverride;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> override container
 */
public abstract class AbstractOrmOverrideContainer<
			O extends OrmOverrideContainer.Owner,
			R extends OrmReadOnlyOverride,
			S extends OrmOverride,
			V extends OrmVirtualOverride,
			X extends XmlOverride
		>
	extends AbstractOrmXmlContextNode
	implements OrmOverrideContainer
{
	// this can be null if the container is "read-only" (i.e. a "null" container)
	protected final O owner;

	protected final Vector<S> specifiedOverrides = new Vector<S>();
	protected final SpecifiedOverrideContainerAdapter specifiedOverrideContainerAdapter = new SpecifiedOverrideContainerAdapter();

	protected final Vector<V> virtualOverrides = new Vector<V>();
	protected final VirtualOverrideContainerAdapter virtualOverrideContainerAdapter = new VirtualOverrideContainerAdapter();


	protected AbstractOrmOverrideContainer(XmlContextNode parent, O owner) {
		super(parent);
		this.owner = owner;
		this.initializeSpecifiedOverrides();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncSpecifiedOverrides();
		// the virtual overrides do not need a sync
	}

	@Override
	public void update() {
		super.update();
		this.updateNodes(this.getSpecifiedOverrides());
		this.updateVirtualOverrides();
	}


	// ********** overrides **********

	@SuppressWarnings("unchecked")
	public ListIterable<R> getOverrides() {
		return new CompositeListIterable<R>(this.getReadOnlySpecifiedOverrides(), this.getReadOnlyVirtualOverrides());
	}

	public int getOverridesSize() {
		return this.specifiedOverrides.size() + this.virtualOverrides.size();
	}

	public R getOverrideNamed(String name) {
		return this.selectOverrideNamed(this.getOverrides(), name);
	}


	// ********** override conversions **********

	/**
	 * <em>Silently</em> add the new virtual override before removing the
	 * specified override, or the <em>update</em> will discover the missing
	 * virtual override and add it preemptively.
	 */
	public V convertOverrideToVirtual(Override_ override) {
		if (override.isVirtual()) {
			throw new IllegalArgumentException("Override is already virtual: " + override); //$NON-NLS-1$
		}

		@SuppressWarnings("unchecked")
		S specifiedOverride = (S) override;
		int virtualIndex = this.virtualOverrides.size();
		String overrideName = specifiedOverride.getName();
		V virtualOverride = null;
		// make sure the specified override actually overrides something before building the virtual override
		if (this.overrideWillBeVirtual(overrideName, specifiedOverride)) {
			virtualOverride = this.buildVirtualOverride(overrideName);
			this.virtualOverrides.add(virtualIndex, virtualOverride);
		}

		this.removeSpecifiedOverride(specifiedOverride);  // trigger update

		if (virtualOverride != null) {
			this.fireItemAdded(VIRTUAL_OVERRIDES_LIST, virtualIndex, virtualOverride);
		}
		return virtualOverride;
	}

	/**
	 * Return whether the specified override name will be a
	 * <em>virtual</em> override when the specified specified override is
	 * removed from the container. The override name must be among the
	 * valid override names and it must not correspond to any of the
	 * remaining specified overrides.
	 */
	protected boolean overrideWillBeVirtual(String overrideName, S specifiedOverrideToBeRemoved) {
		return CollectionTools.contains(this.getPossibleVirtualOverrideNames(), overrideName) &&
				(this.getSpecifiedOverrideNamed(overrideName, specifiedOverrideToBeRemoved) == null);
	}

	/**
	 * <em>Silently</em> remove the virtual override and add the new specified
	 * override before naming the specified override, or the <em>update</em>
	 * will discover the dangling virtual override and remove it preemptively.
	 */
	public S convertOverrideToSpecified(VirtualOverride override) {
		if ( ! override.isVirtual()) {
			throw new IllegalArgumentException("Override is already specified: " + override); //$NON-NLS-1$
		}

		@SuppressWarnings("unchecked")
		V virtualOverride = (V) override;
		int virtualIndex = this.virtualOverrides.indexOf(virtualOverride);
		this.virtualOverrides.remove(virtualIndex);

		int specifiedIndex = this.specifiedOverrides.size();
		X xmlOverride = this.buildXmlOverride();
		S specifiedOverride = this.buildSpecifiedOverride(xmlOverride);
		this.specifiedOverrides.add(specifiedIndex, specifiedOverride);
		this.owner.getXmlOverrides().add(specifiedIndex, xmlOverride);

		this.initializeSpecifiedOverride(specifiedOverride, virtualOverride);  // trigger update

		this.fireItemRemoved(VIRTUAL_OVERRIDES_LIST, virtualIndex, virtualOverride);
		this.fireItemAdded(SPECIFIED_OVERRIDES_LIST, specifiedIndex, specifiedOverride);
		return specifiedOverride;
	}

	protected abstract void initializeSpecifiedOverride(S specifiedOverride, V virtualOverride);


	// ********** specified overrides **********

	public ListIterable<S> getSpecifiedOverrides() {
		return new LiveCloneListIterable<S>(this.specifiedOverrides);
	}

	@SuppressWarnings("unchecked")
	protected ListIterable<R> getReadOnlySpecifiedOverrides() {
		// S should always be a subtype of R, but we can't enforce that in the
		// class declaration...
		return (ListIterable<R>) this.getSpecifiedOverrides();
	}

	public int getSpecifiedOverridesSize() {
		return this.specifiedOverrides.size();
	}

	public S getSpecifiedOverride(int index) {
		return this.specifiedOverrides.get(index);
	}

	public S getSpecifiedOverrideNamed(String name) {
		return this.getSpecifiedOverrideNamed(name, null);
	}

	@SuppressWarnings("unchecked")
	protected S getSpecifiedOverrideNamed(String name, S exclude) {
		return (S) this.selectOverrideNamed(this.getReadOnlySpecifiedOverrides(), name, exclude);
	}

	protected S addSpecifiedOverride() {
		return this.addSpecifiedOverride(this.specifiedOverrides.size());
	}

	protected S addSpecifiedOverride(int index) {
		X xmlOverride = this.buildXmlOverride();
		S override = this.addSpecifiedOverride_(index, xmlOverride);
		this.owner.getXmlOverrides().add(index, xmlOverride);
		return override;
	}

	protected abstract X buildXmlOverride();

	protected void removeSpecifiedOverride(S override) {
		this.removeSpecifiedOverride(this.specifiedOverrides.indexOf(override));
	}

	protected void removeSpecifiedOverride(int index) {
		this.removeSpecifiedOverride_(index);
		this.owner.getXmlOverrides().remove(index);
	}

	protected void removeSpecifiedOverride_(int index) {
		this.removeItemFromList(index, this.specifiedOverrides, SPECIFIED_OVERRIDES_LIST);
	}

	public void moveSpecifiedOverride(int targetIndex, int sourceIndex) {
		this.moveItemInList(targetIndex, sourceIndex, this.specifiedOverrides, SPECIFIED_OVERRIDES_LIST);
		this.owner.getXmlOverrides().move(targetIndex, sourceIndex);
	}

	protected void initializeSpecifiedOverrides() {
		for (X xmlOverride : this.getXmlOverrides()) {
			this.specifiedOverrides.add(this.buildSpecifiedOverride(xmlOverride));
		}
	}

	protected abstract S buildSpecifiedOverride(X xmlOverride);

	protected void syncSpecifiedOverrides() {
		ContextContainerTools.synchronizeWithResourceModel(this.specifiedOverrideContainerAdapter);
	}

	protected Iterable<X> getXmlOverrides() {
		return (this.owner == null) ? EmptyIterable.<X>instance() : this.getXmlOverrides_();
	}

	/**
	 * pre-condition: {@link #owner} is not <code>null</code>
	 */
	protected abstract Iterable<X> getXmlOverrides_();

	protected void moveSpecifiedOverride_(int index, S override) {
		this.moveItemInList(index, override, this.specifiedOverrides, SPECIFIED_OVERRIDES_LIST);
	}

	protected S addSpecifiedOverride_(int index, X xmlOverride) {
		S override = this.buildSpecifiedOverride(xmlOverride);
		this.addItemToList(index, override, this.specifiedOverrides, SPECIFIED_OVERRIDES_LIST);
		return override;
	}

	protected void removeSpecifiedOverride_(S override) {
		this.removeSpecifiedOverride_(this.specifiedOverrides.indexOf(override));
	}

	/**
	 * specified override container adapter
	 */
	protected class SpecifiedOverrideContainerAdapter
		implements ContextContainerTools.Adapter<S, X>
	{
		public Iterable<S> getContextElements() {
			return AbstractOrmOverrideContainer.this.getSpecifiedOverrides();
		}
		public Iterable<X> getResourceElements() {
			return AbstractOrmOverrideContainer.this.getXmlOverrides();
		}
		@SuppressWarnings("unchecked")
		public X getResourceElement(S contextElement) {
			return (X) contextElement.getXmlOverride();
		}
		public void moveContextElement(int index, S element) {
			AbstractOrmOverrideContainer.this.moveSpecifiedOverride_(index, element);
		}
		public void addContextElement(int index, X resourceElement) {
			AbstractOrmOverrideContainer.this.addSpecifiedOverride_(index, resourceElement);
		}
		public void removeContextElement(S element) {
			AbstractOrmOverrideContainer.this.removeSpecifiedOverride_(element);
		}
	}


	// ********** virtual overrides **********

	public ListIterable<V> getVirtualOverrides() {
		return new LiveCloneListIterable<V>(this.virtualOverrides);
	}

	@SuppressWarnings("unchecked")
	protected ListIterable<R> getReadOnlyVirtualOverrides() {
		// V should always be a subtype of R, but we can't enforce that in the
		// class declaration...
		return (ListIterable<R>) this.getVirtualOverrides();
	}

	public int getVirtualOverridesSize() {
		return this.virtualOverrides.size();
	}

	protected void updateVirtualOverrides() {
		ContextContainerTools.update(this.virtualOverrideContainerAdapter);
	}

	/**
	 * Return the overridable names that are not already in the list of
	 * specified overrides.
	 */
	protected Iterable<String> getVirtualOverrideNames() {
		return new FilteringIterable<String>(this.getPossibleVirtualOverrideNames()) {
			@Override
			protected boolean accept(String name) {
				return AbstractOrmOverrideContainer.this.overrideIsVirtual(name);
			}
		};
	}

	protected boolean overrideIsVirtual(String name) {
		return this.getSpecifiedOverrideNamed(name) == null;
	}

	protected void moveVirtualOverride(int index, V override) {
		this.moveItemInList(index, override, this.virtualOverrides, VIRTUAL_OVERRIDES_LIST);
	}

	protected V addVirtualOverride(int index, String name) {
		V override = this.buildVirtualOverride(name);
		this.addItemToList(index, override, this.virtualOverrides, VIRTUAL_OVERRIDES_LIST);
		return override;
	}

	protected abstract V buildVirtualOverride(String name);

	protected void removeVirtualOverride(V override) {
		this.removeItemFromList(override, this.virtualOverrides, VIRTUAL_OVERRIDES_LIST);
	}

	/**
	 * virtual override container adapter
	 * NB: the context override is matched with a resource override by name
	 */
	protected class VirtualOverrideContainerAdapter
		implements ContextContainerTools.Adapter<V, String>
	{
		public Iterable<V> getContextElements() {
			return AbstractOrmOverrideContainer.this.getVirtualOverrides();
		}
		public Iterable<String> getResourceElements() {
			return AbstractOrmOverrideContainer.this.getVirtualOverrideNames();
		}
		public String getResourceElement(V contextElement) {
			return contextElement.getName();
		}
		public void moveContextElement(int index, V element) {
			AbstractOrmOverrideContainer.this.moveVirtualOverride(index, element);
		}
		public void addContextElement(int index, String resourceElement) {
			AbstractOrmOverrideContainer.this.addVirtualOverride(index, resourceElement);
		}
		public void removeContextElement(V element) {
			AbstractOrmOverrideContainer.this.removeVirtualOverride(element);
		}
	}


	// ********** misc **********

	@Override
	public XmlContextNode getParent() {
		return (XmlContextNode) super.getParent();
	}

	public TypeMapping getOverridableTypeMapping() {
		return this.owner.getOverridableTypeMapping();
	}

	public TypeMapping getTypeMapping() {
		return this.owner.getTypeMapping();
	}

	/**
	 * Return all the possible virtual override names. If we have a
	 * corresponding Java entity, take the override names from it. This allows
	 * us to include any Java specified overrides that are invalid (and
	 * generate the appropriate error messages). If we don't have a
	 * corresponding Java entity, take the override names directly from the
	 * type.
	 */
	protected Iterable<String> getPossibleVirtualOverrideNames() {
		if (this.owner == null) {
			return EmptyIterable.instance();
		}
		Iterable<String> javaNames = this.owner.getJavaOverrideNames();
		return (javaNames != null) ? javaNames : this.owner.getAllOverridableNames();
	}

	public Iterable<String> getAllOverridableNames() {
		return (this.owner != null) ? this.owner.getAllOverridableNames() : EmptyIterable.<String>instance();
	}

	public boolean tableNameIsInvalid(String tableName) {
		return this.owner.tableNameIsInvalid(tableName);
	}

	public Iterable<String> getCandidateTableNames() {
		return this.owner.getCandidateTableNames();
	}

	public org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName) {
		return this.owner.resolveDbTable(tableName);
	}

	public String getDefaultTableName() {
		return this.owner.getDefaultTableName();
	}

	public JptValidator buildOverrideValidator(ReadOnlyOverride override, OverrideTextRangeResolver textRangeResolver) {
		return this.owner.buildOverrideValidator(override, this, textRangeResolver);
	}

	public JptValidator buildColumnValidator(ReadOnlyOverride override, ReadOnlyBaseColumn column, ReadOnlyBaseColumn.Owner columnOwner, BaseColumnTextRangeResolver textRangeResolver) {
		return this.owner.buildColumnValidator(override, column, columnOwner, textRangeResolver);
	}

	protected R selectOverrideNamed(Iterable<R> overrides, String name) {
		return this.selectOverrideNamed(overrides, name, null);
	}

	protected R selectOverrideNamed(Iterable<R> overrides, String name, S exclude) {
		for (R override : overrides) {
			if (override == exclude) {
				continue;  // skip
			}
			if (this.valuesAreEqual(override.getName(), name)) {
				return override;
			}
		}
		return null;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		for (R override : this.getOverrides()) {
			override.validate(messages, reporter);
		}
	}

	public TextRange getValidationTextRange() {
		return (this.specifiedOverrides.size() > 0) ?
				this.specifiedOverrides.get(0).getValidationTextRange() :
				this.owner.getValidationTextRange();
	}
}
