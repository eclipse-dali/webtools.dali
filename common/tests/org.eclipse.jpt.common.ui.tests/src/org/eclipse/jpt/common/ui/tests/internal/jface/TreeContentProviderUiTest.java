/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.tests.internal.jface;

import java.util.ArrayList;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.ui.internal.jface.ItemTreeStateProviderManager;
import org.eclipse.jpt.common.ui.internal.jface.ModelItemStructuredContentProvider;
import org.eclipse.jpt.common.ui.internal.jface.ModelItemTreeContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemStructuredContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemStructuredContentProvider.Manager;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.StaticCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

@SuppressWarnings("nls")
public class TreeContentProviderUiTest
	extends ApplicationWindow
{
	/* CU private */ final Root root;

	/* CU private */ ModifiablePropertyValueModel<TreeNode> selectedNodeModel;

	/* CU private */ TreeViewer controlTreeViewer;

	private TreeViewer viewTreeViewer;

	private Text nodeNameText;


	public static void main(String[] args) {
		Window window = new TreeContentProviderUiTest();
		window.setBlockOnOpen(true);
		window.open();

		Display.getCurrent().dispose();
		System.exit(0);
	}

	private TreeContentProviderUiTest() {
		super(null);
		this.root = this.buildRoot();
		this.selectedNodeModel = new SimplePropertyValueModel<TreeNode>(this.root);
	}

	private Root buildRoot() {
		Root r = new Root();
		Parent parent1 = r.addChild("Parent 1");
		parent1.addChild("Foo");
		parent1.addChild("Bar");
		parent1.addChild("Baz");
		Parent parent2 = r.addChild("Parent 2");
		parent2.addChild("Joo");
		parent2.addChild("Jar");
		parent2.addChild("Jaz");
		Parent parent3 = r.addChild("Parent 3");
		parent3.addNestedChild("Too");
		parent3.addNestedChild("Tar");
		parent3.addNestedChild("Taz");
		return r;
	}

	@Override
	protected Control createContents(Composite parent) {
		((Shell) parent).setText(this.getClass().getSimpleName());
		parent.setSize(800, 400);
		parent.setLayout(new GridLayout());
		Composite mainPanel = new Composite(parent, SWT.NONE);
		mainPanel.setLayoutData(new GridData(GridData.FILL_BOTH));
		mainPanel.setLayout(new GridLayout());
		this.buildTreePanel(mainPanel);
		this.buildControlPanel(mainPanel);
		return mainPanel;
	}

	private void buildTreePanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);
		panel.setLayoutData(new GridData(GridData.FILL_BOTH));
		panel.setLayout(new GridLayout(2, true));
		this.buildControlTreePanel(panel);
		this.buildViewTreePanel(panel);
	}

	private void buildControlTreePanel(Composite parent) {
		this.controlTreeViewer = this.buildTreePanel(
				parent, "Control tree",
				new ItemTreeStateProviderManager(new ControlItemTreeContentProviderFactory(), JFaceResources.getResources()),
				new LabelProvider());
		this.controlTreeViewer.addSelectionChangedListener(this.buildTreeSelectionChangedListener());
		this.selectedNodeModel.addPropertyChangeListener(
				PropertyValueModel.VALUE,
				new PropertyChangeListener() {
					public void propertyChanged(PropertyChangeEvent event) {
						TreeContentProviderUiTest.this.controlTreeViewer.setSelection(new StructuredSelection(event.getNewValue()));
					}
				}
			);
	}

	private void buildViewTreePanel(Composite parent) {
		this.viewTreeViewer = this.buildTreePanel(
				parent, "View tree",
				new ItemTreeStateProviderManager(new ViewItemTreeContentProviderFactory(), JFaceResources.getResources()),
				new LabelProvider());
	}

	private TreeViewer buildTreePanel(Composite parent, String labelText, ITreeContentProvider contentProvider, ILabelProvider labelProvider) {
		Composite panel = new Composite(parent, SWT.NONE);
		panel.setLayoutData(new GridData(GridData.FILL_BOTH));
		panel.setLayout(new GridLayout());

		Label label = new Label(panel, SWT.LEFT);
		label.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));
		label.setText(labelText);

		final TreeViewer tree = new TreeViewer(panel, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		tree.getTree().setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		tree.setContentProvider(contentProvider);
		tree.setLabelProvider(labelProvider);
		tree.setInput(this.root);

		return tree;
	}

	private ISelectionChangedListener buildTreeSelectionChangedListener() {
		return new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				TreeNode selection = (TreeNode) ((IStructuredSelection) event.getSelection()).getFirstElement();
				TreeContentProviderUiTest.this.selectedNodeModel.setValue((selection == null) ? TreeContentProviderUiTest.this.root : selection);
			}
		};
	}

	private void buildControlPanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);
		panel.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
		panel.setLayout(new GridLayout(6, false));
		this.buildNodeNameText(panel);
		this.buildAddChildACI().fill(panel);
		this.buildAddNestedChildACI().fill(panel);
		this.buildRemoveACI().fill(panel);
		this.buildClearModelACI().fill(panel);
		this.buildRestoreModelACI().fill(panel);
	}

	private void buildNodeNameText(Composite parent) {
		this.nodeNameText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		this.nodeNameText.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
	}

	private ActionContributionItem buildAddChildACI() {
		final Action action = new Action("Add child", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				TreeContentProviderUiTest.this.addChild();
			}
		};
		action.setToolTipText("Add a child with the given name");
		this.selectedNodeModel.addPropertyChangeListener(
				PropertyValueModel.VALUE,
				new PropertyChangeListener() {
					public void propertyChanged(PropertyChangeEvent event) {
						action.setEnabled(((TreeNode) event.getNewValue()).canHaveChildren());
					}
				}
			);
		return new ActionContributionItem(action);
	}

	private ActionContributionItem buildAddNestedChildACI() {
		final Action action = new Action("Add nested child", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				TreeContentProviderUiTest.this.addNestedChild();
			}
		};
		action.setToolTipText("Add a nested child with the given name");
		action.setEnabled(false);
		this.selectedNodeModel.addPropertyChangeListener(
				PropertyValueModel.VALUE,
				new PropertyChangeListener() {
					public void propertyChanged(PropertyChangeEvent event) {
						action.setEnabled(((TreeNode) event.getNewValue()).canHaveNestedChildren());
					}
				}
			);
		return new ActionContributionItem(action);
	}

	private ActionContributionItem buildRemoveACI() {
		final Action action = new Action("Remove", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				TreeContentProviderUiTest.this.remove();
			}
		};
		action.setToolTipText("Remove the selected node");
		action.setEnabled(false);
		this.selectedNodeModel.addPropertyChangeListener(
				PropertyValueModel.VALUE,
				new PropertyChangeListener() {
					public void propertyChanged(PropertyChangeEvent event) {
						action.setEnabled(event.getNewValue() != TreeContentProviderUiTest.this.root);
					}
				}
			);
		return new ActionContributionItem(action);
	}

	private ActionContributionItem buildClearModelACI() {
		Action action = new Action("Clear model", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				TreeContentProviderUiTest.this.clearModel();
			}
		};
		action.setToolTipText("Clear the model");
		return new ActionContributionItem(action);
	}

	private ActionContributionItem buildRestoreModelACI() {
		Action action = new Action("Restore model", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				TreeContentProviderUiTest.this.restoreModel();
			}
		};
		action.setToolTipText("Restore the model");
		return new ActionContributionItem(action);
	}

	void addChild() {
		String nodeName = this.nodeNameText.getText();
		if (nodeName.length() != 0) {
			new Thread(new AddChildRunnable(nodeName)).start();
		}
	}

	private class AddChildRunnable
		implements Runnable
	{
		private final String name;
		AddChildRunnable(String name) {
			super();
			this.name = name;
		}
		public void run() {
			TreeContentProviderUiTest.this.addChild_(this.name);
		}
	}

	void addChild_(String name) {
		this.selectedNodeModel.getValue().addChild(name);
	}

	void addNestedChild() {
		String nodeName = this.nodeNameText.getText();
		if (nodeName.length() != 0) {
			this.selectedNodeModel.getValue().addNestedChild(nodeName);
		}
	}

	void remove() {
		this.selectedNodeModel.getValue().remove();
	}

	void clearModel() {
		this.controlTreeViewer.setInput(null);
		this.viewTreeViewer.setInput(null);
	}

	void restoreModel() {
		this.controlTreeViewer.setInput(this.root);
		this.viewTreeViewer.setInput(this.root);
	}


	/* CU private */ static abstract class AbstractItemTreeContentProviderFactory
		implements ItemTreeContentProvider.Factory
	{
		public ItemStructuredContentProvider buildProvider(Object input, Manager manager) {
			return new ModelItemStructuredContentProvider(input, buildChildrenModel((Root) input), manager);
		}
		public ItemTreeContentProvider buildProvider(Object item, Object parent, ItemTreeContentProvider.Manager manager) {
			return new ModelItemTreeContentProvider(item, parent, buildChildrenModel((TreeNode) item), manager);
		}
	}

	/* CU private */ static CollectionValueModel<TreeNode> buildChildrenModel(TreeNode item) {
		return new ListCollectionValueModelAdapter<TreeNode>(
				new ListAspectAdapter<TreeNode, TreeNode>(TreeNode.CHILDREN_LIST, item) {
					@Override
					protected ListIterable<TreeNode> getListIterable() {
						return this.subject.getChildren();
					}
				}
			);
	}


	/* CU private */ static class ControlItemTreeContentProviderFactory
		extends AbstractItemTreeContentProviderFactory
	{
		// nothing
	}


	/* CU private */ static class ViewItemTreeContentProviderFactory
		extends AbstractItemTreeContentProviderFactory
	{
		@Override
		public ItemTreeContentProvider buildProvider(Object item, Object parent, ItemTreeContentProvider.Manager manager) {
			if (item instanceof Parent) {
				return new ModelItemTreeContentProvider(item, parent, buildParentChildrenModel((Parent) item), manager);
			}
			if (item instanceof Nest) {
				parent = ((TreeNode) parent).parent();
			}
			return super.buildProvider(item, parent, manager);
		}
	}

	/* CU private */ static CollectionValueModel<TreeNode> buildParentChildrenModel(Parent item) {
		return new CompositeCollectionValueModel<TreeNode, TreeNode>(buildChildrenModel(item), TREE_NODE_TRANSFORMER);
	}

	/* CU private */ static Transformer<TreeNode, CollectionValueModel<TreeNode>> TREE_NODE_TRANSFORMER = new TreeNodeTransformer();
	/* CU private */ static class TreeNodeTransformer
		extends TransformerAdapter<TreeNode, CollectionValueModel<TreeNode>>
	{
		@Override
		public CollectionValueModel<TreeNode> transform(TreeNode value) {
			return (value instanceof Nest) ?
						this.transform((Nest) value) :
						new StaticCollectionValueModel<TreeNode>(CollectionTools.hashBag(value));
		}

		private CollectionValueModel<TreeNode> transform(Nest nest) {
			return new ListCollectionValueModelAdapter<TreeNode>(
					new ListAspectAdapter<TreeNode, TreeNode>(TreeNode.CHILDREN_LIST, nest) {
						@Override
						protected ListIterable<TreeNode> getListIterable() {
							return this.subject.getChildren();
						}
					}
				);
		}
	}


	/* CU private */ static class LabelProvider
		extends BaseLabelProvider
		implements ILabelProvider
	{
		public Image getImage(Object element) {
			return null;
		}

		public String getText(Object element) {
			return ((TreeNode) element).getName();
		}
	}


	/* CU private */ static abstract class TreeNode
		extends AbstractModel
	{
		private TreeNode parent;

		protected final ArrayList<TreeNode> children = new ArrayList<TreeNode>();
		public final static String CHILDREN_LIST = "children";

		protected String name;
		public final static String NAME_PROPERTY = "name";


		TreeNode(TreeNode parent, String name) {
			this.parent = parent;
			this.name = name;
		}

		public TreeNode parent() {
			return this.parent;
		}

		public ListIterable<TreeNode> getChildren() {
			return IterableTools.cloneLive(this.children);
		}

		protected <TN extends TreeNode> TN addChild(TN child) {
			this.addItemToList(child, this.children, CHILDREN_LIST);
			return child;
		}

		public void remove() {
			this.parent.removeChild(this);
		}

		public void removeChild(TreeNode child) {
			this.removeItemFromList(child, this.children, CHILDREN_LIST);
		}

		public void removeChild(int index) {
			this.removeItemFromList(index, this.children, CHILDREN_LIST);
		}

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			String old = this.name;
			this.name = name;
			this.firePropertyChanged(NAME_PROPERTY, old, name);
		}

		public boolean canHaveChildren() {
			return false;
		}

		public TreeNode addChild(@SuppressWarnings("unused") String childName) {
			throw new UnsupportedOperationException();
		}

		public boolean canHaveNestedChildren() {
			return false;
		}

		public TreeNode addNestedChild(@SuppressWarnings("unused") String childName) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void toString(StringBuilder sb) {
			sb.append(this.name);
		}
	}


	/* CU private */ static class Root
		extends TreeNode
	{
		Root() {
			super(null, null);
		}

		@Override
		public boolean canHaveChildren() {
			return true;
		}

		@Override
		public Parent addChild(String childName) {
			return this.addChild(new Parent(this, childName));
		}
	}


	/* CU private */ static class Parent
		extends TreeNode
	{
		Parent(TreeNode parent, String name) {
			super(parent, name);
		}

		@Override
		public boolean canHaveChildren() {
			return true;
		}

		@Override
		public Child addChild(String childName) {
			return this.addChild(new Child(this, childName));
		}

		@Override
		public boolean canHaveNestedChildren() {
			return true;
		}

		@Override
		public Nest addNestedChild(String childName) {
			Nest nest = new Nest(this);
			this.addChild(nest);
			nest.addChild(childName);
			return nest;
		}

		public Iterable<Child> getNestlessChildren() {
			return IterableTools.transform(this.getChildren(), CHILD_TREE_NODE_TRANSFORMER);
		}

		public static final Transformer<TreeNode, Child> CHILD_TREE_NODE_TRANSFORMER = new ChildTreeNodeTransformer();
		public static class ChildTreeNodeTransformer
			extends TransformerAdapter<TreeNode, Child>
		{
			@Override
			public Child transform(TreeNode node) {
				return (node instanceof Nest) ? ((Nest) node).getChild() : (Child) node;
			}
		}
	}


	/* CU private */ static class Nest
		extends TreeNode
	{
		Nest(TreeNode parent) {
			super(parent, "nest");
		}

		@Override
		public boolean canHaveChildren() {
			return this.children.size() == 0;
		}

		@Override
		public Child addChild(String childName) {
			return this.addChild(new Child(this, childName));
		}

		/* can only have one child */
		public Child getChild() {
			return (this.children.isEmpty()) ? null : (Child) this.children.get(0);
		}
	}


	/* CU private */ static class Child
		extends TreeNode
	{
		Child(TreeNode parent, String name) {
			super(parent, name);
		}
	}
}
