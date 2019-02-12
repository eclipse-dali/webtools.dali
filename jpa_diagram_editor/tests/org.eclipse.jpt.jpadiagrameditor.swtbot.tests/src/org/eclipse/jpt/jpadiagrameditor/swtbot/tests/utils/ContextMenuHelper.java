/*******************************************************************************
 * Copyright (c) 2013, 2019 IBM Corporation and others.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.swtbot.tests.utils;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withMnemonic;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withRegex;
import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellIsActive;
import static org.eclipse.swtbot.swt.finder.waits.Conditions.widgetIsEnabled;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

import java.text.MessageFormat;
import java.util.Arrays;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.results.WidgetResult;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.AbstractSWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.hamcrest.Matcher;

@SuppressWarnings("restriction")
public class ContextMenuHelper
{

    /**
     * Clicks the context menu matching the text.
     *
     * @param text
     *          the text on the context menu.
     * @throws WidgetNotFoundException
     *           if the widget is not found.
     */
	public static void clickContextMenu( final AbstractSWTBot<?> bot, final String... texts )
    {
        final Matcher<?>[] matchers = new Matcher<?>[texts.length];
        for ( int i = 0; i < texts.length; i++ )
        {
            // matchers[i] = allOf( instanceOf( MenuItem.class ), withMnemonic( texts[i] ) );
            matchers[i] = allOf( instanceOf( MenuItem.class ), withRegex( texts[i] ) );
        }

        // show
        final MenuItem menuItem = UIThreadRunnable.syncExec( new WidgetResult<MenuItem>()
        {
            public MenuItem run()
            {
                MenuItem menuItem = null;
                Control control = ( Control ) bot.widget;
                Menu menu = control.getMenu();
                for ( int i = 0; i < matchers.length; i++ )
                {
                    menuItem = show( menu, matchers[i] );
                    if ( menuItem != null )
                    {
                        menu = menuItem.getMenu();
                    }
                    else
                    {
                        hide( menu );
                        break;
                    }
                }

                return menuItem;
            }
        } );
        if ( menuItem == null )
        {
            throw new WidgetNotFoundException( "Could not find menu: " + Arrays.asList( texts ) );
        }

        // click
        click( menuItem );

        // hide
        UIThreadRunnable.syncExec( new VoidResult()
        {
            public void run()
            {
                hide( menuItem.getParent() );
            }
        } );
    }

    /**
	 * Clicks the menu from the menu bar matching the given text in the active
	 * shell.
	 * 
	 * @param texts
	 *            the text on the context menu.
	 * @throws WidgetNotFoundException
	 *             if the widget is not found.
	 */
	public static void clickMenu(final String... texts) {
		clickMenu(new SWTWorkbenchBot().activeShell(), texts);
	}

	/**
	 * Clicks the menu from the menu bar matching the given text.
	 * 
	 * @param bot
	 *            the widget.
	 * @param texts
	 *            the text on the context menu.
	 * @throws WidgetNotFoundException
	 *             if the widget is not found.
	 */
	public static MenuItem clickMenu(final AbstractSWTBot<?> bot, final String... texts) {
		return clickMenu(bot, false, texts);
	}

	/**
	 * Clicks the menu from the menu bar matching the given text and toggles the
	 * selection of the menu item
	 * 
	 * @param bot
	 *            the widget.
	 * @param texts
	 *            the text on the context menu.
	 * @throws WidgetNotFoundException
	 *             if the widget is not found.
	 */
	public static void clickMenuToggleSeletion(final AbstractSWTBot<?> bot, final String... texts) {
		clickMenu(bot, true, texts);
	}

	/**
	 * Clicks the menu from the menu bar matching the given text and toggles the
	 * selection of the menu item
	 * 
	 * @param bot
	 *            the widget.
	 * @param texts
	 *            the text on the context menu.
	 * @param toggleSelection
	 *            a flag that shows if the selection of the menu item should be
	 *            toggled
	 * @throws WidgetNotFoundException
	 *             if the widget is not found.
	 */
	private static MenuItem clickMenu(final AbstractSWTBot<?> bot, boolean toggleSelection, final String... texts) {
		// show
		final MenuItem menuItem = UIThreadRunnable.syncExec(new WidgetResult<MenuItem>() {
			@Override
			public MenuItem run() {
				MenuItem item = null;
				Shell control = (Shell) bot.widget;
				Menu menu = control.getMenuBar();
				for (String text : texts) {
					Matcher<?> matcher = allOf(instanceOf(MenuItem.class), withMnemonic(text));
					item = show(menu, matcher);
					if (item != null) {
						menu = item.getMenu();
					} else {
						hide(menu);
						break;
					}
				}

				return item;
			}
		});
		if (menuItem == null) {
			throw new WidgetNotFoundException("Could not find menu: " + Arrays.asList(texts));
		}

		// click
		new SWTWorkbenchBot().waitUntil(Conditions.widgetIsEnabled(new SWTBotMenu(menuItem)));
		click(menuItem, toggleSelection);

		// hide
		UIThreadRunnable.syncExec(new VoidResult() {
			@Override
			public void run() {
				hide(menuItem.getParent());
			}
		});
		
		return menuItem;
	}

    private static MenuItem show( final Menu menu, final Matcher<?> matcher )
    {
        if ( menu != null )
        {
            menu.notifyListeners( SWT.Show, new Event() );
            MenuItem[] items = menu.getItems();
            for ( final MenuItem menuItem : items )
            {
                if ( matcher.matches( menuItem ) )
                {
                    return menuItem;
                }
            }
            menu.notifyListeners( SWT.Hide, new Event() );
        }
        return null;
    }


    private static void click( final MenuItem menuItem )
    {
        final Event event = new Event();
        event.time = ( int ) System.currentTimeMillis();
        event.widget = menuItem;
        event.display = menuItem.getDisplay();
        event.type = SWT.Selection;

        UIThreadRunnable.asyncExec( menuItem.getDisplay(), new VoidResult()
        {
            public void run()
            {
                menuItem.notifyListeners( SWT.Selection, event );
            }
        } );
    }
    
    /**
	 * Click on a menu item
	 * 
	 * @param menuItem
	 *            the menu item that will click
	 * @param toogleSelection
	 *            a flag that shows if the selection of the menu item should be
	 *            toggled
	 */
	private static void click(final MenuItem menuItem, final boolean toogleSelection) {
		final Event event = new Event();
		event.time = (int) System.currentTimeMillis();
		event.widget = menuItem;
		event.display = menuItem.getDisplay();
		event.type = SWT.Selection;

		UIThreadRunnable.asyncExec(menuItem.getDisplay(), new VoidResult() {
			@Override
			public void run() {
				boolean seleted = true;
				if (toogleSelection && (SWTUtils.hasStyle(menuItem, SWT.CHECK) | SWTUtils.hasStyle(menuItem, SWT.RADIO))) {
					seleted = !menuItem.getSelection();
				}
				menuItem.setSelection(seleted);
				menuItem.notifyListeners(SWT.Selection, event);
			}
		});
	}


    private static void hide( final Menu menu )
    {
        menu.notifyListeners( SWT.Hide, new Event() );
        if ( menu.getParentMenu() != null )
        {
            hide( menu.getParentMenu() );
        }
    }
    
    
    /**
	 * Selects an tree item in the browser tree and expands the selected tree
	 * item. Takes care that all attributes and child entries are initialized so
	 * that there are no pending background actions and event notifications.
	 * This is necessary to avoid race conditions.
	 * 
	 * @param tree
	 *            the browser tree
	 * @param path
	 *            the path to the tree item
	 * @return the selected tree item as SWTBotTreeItem
	 * @throws WidgetNotFoundException
	 *             when the tree item is not found in the tree
	 */
	public static SWTBotTreeItem selectTreeItem(final SWTBotTree tree, final String... path) throws WidgetNotFoundException {
		return selectTreeItem(tree, true, path);
	}

	/**
	 * Selects an tree item in the browser tree and optionally expands the
	 * selected tree item. Takes care that all attributes and child entries are
	 * initialized so that there are no pending background actions and event
	 * notifications. This is necessary to avoid race conditions.
	 * 
	 * @param tree
	 *            the browser tree
	 * @param expandChild
	 *            true to expand the child tree item
	 * @param path
	 *            the path to the tree item
	 * @return the selected tree item as SWTBotTreeItem
	 * @throws WidgetNotFoundException
	 *             when the tree item is not found in the tree
	 */
	public static SWTBotTreeItem selectTreeItem(final SWTBotTree tree, final boolean expandChild, final String... path)
			throws WidgetNotFoundException {
		tree.unselect();

		SWTBotTreeItem treeItem = getTreeItem(tree, expandChild, path);
		waitForWidgetEnabled(treeItem);
		treeItem.select();
		return treeItem;
	}
	
	/**
	 * Finds a tree item in a tree and and optionally expands the found tree
	 * item. Takes care that all attributes and child entries are initialized so
	 * that there are no pending background actions and event notifications.
	 * This is necessary to avoid race conditions.
	 * 
	 * @param tree
	 *            the tree
	 * @param expandChild
	 *            true to expand the found tree item
	 * @param path
	 *            the path to the tree item
	 * @return the tree item as SWTBotTreeItem
	 * @throws WidgetNotFoundException
	 *             when the tree item is not found in the tree
	 */
	public static SWTBotTreeItem getTreeItem(final SWTBotTree tree, final boolean expandChild, final String... path) throws WidgetNotFoundException {
		Assert.isTrue(path.length > 0);
		SWTBotTreeItem treeItem = null;
//		try {
			// wait for the first item
			new SWTBot().waitUntil(new DefaultCondition() {
				@Override
				public boolean test() throws Exception {
					try {
						tree.getTreeItem(path[0]);
						return true;
					} catch (WidgetNotFoundException wnfe) {
						return false;
					}
				}

				@Override
				public String getFailureMessage() {
					return "Could not find node with text " + path[0]; //$NON-NLS-1$
				}
			});

			treeItem = tree.getTreeItem(path[0]);
			for (int i = 1; i < path.length; i++) {
				if (treeItem.rowCount() > 0) {
					waitForWidgetEnabled(tree);
					treeItem.expand();
				}

				final String nodeText = path[i];
				final SWTBotTreeItem currentTreeItem = treeItem;

				// wait until the node appears in the
				// expanded subtree
				// as the expansion is executed
				// asynchronously in the UI thread
				new SWTBot().waitUntil(new DefaultCondition() {
					@Override
					public boolean test() throws Exception {
						try {
							currentTreeItem.getNode(nodeText);
							return true;
						} catch (WidgetNotFoundException wnfe) {
							currentTreeItem.collapse();
							currentTreeItem.expand();

							return false;
						}
					}

					@Override
					public String getFailureMessage() {
						return "Could not find node with text " + nodeText; //$NON-NLS-1$
					}
				});

				treeItem = treeItem.getNode(nodeText);
			}
			if (treeItem.widget.isDisposed()) {
				getTreeItem(tree, expandChild, path);
			} else {
				if (expandChild && (treeItem.rowCount() > 0)) {
					waitForWidgetEnabled(tree);
					treeItem.expand();
				}
			}

//		} catch (TimeoutException te) {
//			throw new WidgetNotFoundException(te.getMessage());
//		}
		return treeItem;
	}
	
	/**
	 * Waits until a widget is enabled
	 * 
	 * @param widget
	 *            the widget
	 */
	public static void waitForWidgetEnabled(AbstractSWTBot<? extends Widget> widget) {
		waitForWidgetEnabled(widget, SWTBotPreferences.DEFAULT_POLL_DELAY);
	}

	/**
	 * Waits until a widget is enabled
	 * 
	 * @param widget
	 * @param pollDelay
	 *            the interval on which the check is performed
	 */
	public static void waitForWidgetEnabled(AbstractSWTBot<? extends Widget> widget, long pollDelay) {
		new SWTWorkbenchBot().waitUntil(widgetIsEnabled(widget), SWTBotPreferences.TIMEOUT, pollDelay);
	}
	
	public static SWTBotTreeItem selectNodeInTree(SWTBotTree tree, String... path) {
		SWTBotTreeItem firstLevelTreeItem = tree.getTreeItem(path[0]);
		firstLevelTreeItem.select();
		firstLevelTreeItem.click();
		firstLevelTreeItem.expand();
		if (path.length > 1) {
			String[] newPath = Arrays.copyOfRange(path, 1, path.length);
			return selectSubnode(firstLevelTreeItem, newPath);
		}
		return firstLevelTreeItem;
	}

	private static SWTBotTreeItem selectSubnode(SWTBotTreeItem parentNode, String... path) {
		SWTBotTreeItem resultTreeItem = parentNode;
		for (int i = 0; i < path.length; i++) {
			resultTreeItem = parentNode.getNode(path[i]);
			resultTreeItem.select();
			resultTreeItem.click();
			resultTreeItem.expand();
			if (i < (path.length - 1)) {
				String[] newPath = Arrays.copyOfRange(path, 1, path.length);
				return selectSubnode(resultTreeItem, newPath);
			}
		}
		return resultTreeItem;
	}
	
	/**
	 * Open properties in the given path
	 * 
	 * @param contextMenu
	 *            the name of the context menu
	 * @param pathToResource
	 *            the path to the resource
	 * @return the workbench bot
	 */
	public static SWTWorkbenchBot openProjectProperties(SWTWorkbenchBot bot, String projectName) {

		bot.viewByTitle("Project Explorer").show();
		SWTBotTree resourcesTree = bot.tree();

		// Depending on the previous operation the tree can
		// be disabled.
		// This will fail expanded operations below. That is
		// way we need wait condition.
		bot.waitUntil(widgetIsEnabled(resourcesTree));
		selectTreeItem(resourcesTree, projectName);
		clickContextMenu(resourcesTree, WorkbenchMessages.Workbench_properties);

		bot.waitUntil(shellIsActive(MessageFormat.format(WorkbenchMessages.PropertyDialog_propertyMessage, projectName)), 20000);
		SWTBotShell shell = bot.shell(MessageFormat.format(WorkbenchMessages.PropertyDialog_propertyMessage, projectName));
		shell.activate();
		return bot;
	}
}