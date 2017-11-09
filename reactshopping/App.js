/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View
} from 'react-native';
import Main from "./Main";

const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' +
    'Cmd+D or shake for dev menu',
  android: 'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

export default class App extends Component<{}> {
  comp:Component;
  constructor(props) {
    super(props);
    this.state={shoppingLists:"", component:""};
    this.state.shoppingLists=[];
    this.comp = <Main change={this.changeComponent.bind(this)} update={this.updateShoppingList.bind(this)} add={this.addShoppingList.bind(this)} list={this.state.shoppingLists}/>;
    this.state.component=this.comp;
  }

  updateShoppingList(index, elem) {
    let shoppingLists = this.state.shoppingLists;
    shoppingLists[index] = elem;
    this.setState({shoppingLists:shoppingLists});
  }

  addShoppingList(elem) {
    let shoppingLists = this.state.shoppingLists;
    shoppingLists.push(elem);
    this.setState({shoppingLists:shoppingLists});
  }

  changeComponent(comp) {
    this.setState({component:comp});
  }


  render() {
    return (
        this.state.component
    );

  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});