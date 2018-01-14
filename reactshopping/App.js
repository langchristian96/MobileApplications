/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, {Component} from 'react';
import {
    Platform,
    StyleSheet,
    Text,
    View, AsyncStorage
} from 'react-native';
import Main from "./Main";
import SeeAll from "./SeeAll";
import firebase from 'react-native-firebase';
import {Login} from "./Login";

const instructions = Platform.select({
    ios: 'Press Cmd+R to reload,\n' +
    'Cmd+D or shake for dev menu',
    android: 'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

export default class App extends Component<{}> {
    comp: Component;
    newComp: Component;

    constructor(props) {
        super(props);
        AsyncStorage.setItem('test','1');
        AsyncStorage.getItem('test').then((value,err)=>{
            console.log("test: ",value,err);
        });
        this.crtindex = 0;
        this.state = {shoppingLists: "", component: ""};
        this.state.shoppingLists = [];
        this.comp=<Login login={this.loginUser.bind(this)}/>;
        // this.comp = <Main change={this.changeComponent.bind(this)} update={this.updateShoppingList.bind(this)}
        //                   add={this.addShoppingList.bind(this)} list={this.state.shoppingLists} delete={this.deleteShoppingList.bind(this)}/>;
        this.state.component = this.comp;
        AsyncStorage.getItem('crtindex').then((value, err) => {
            console.log(err,value);
            if (!err && value != null) {
                this.crtindex = JSON.parse(value);
            }
            else {
                AsyncStorage.setItem('crtindex', JSON.stringify(this.crtindex));
            }
            firebase.database().ref('crtindex').set(this.crtindex);

        });
        // AsyncStorage.setItem('shoppingLists',JSON.stringify(this.state.shoppingLists));
        AsyncStorage.getItem('shoppingLists').then((value, err) => {
            console.log(err,value);
            if (!err && value != null) {
                console.log("Expected sls set: ",value);
                this.setState({shoppingLists: JSON.parse(value)});
                console.log("New main render list",this.state.shoppingLists);
                firebase.database().ref('shoppingLists').set(JSON.parse(value));
            }
            else {
                console.log("sl not set");
                this.setState({shoppingLists: []});
                AsyncStorage.setItem('shoppingLists',JSON.stringify(this.state.shoppingLists));
                firebase.database().ref('shoppingLists').set([]);
            }
        });
    }

    getShoppingList() {
        return this.state.shoppingLists;
    }

    loginUser(username) {
        console.log("login function");
        this.newComp = <Main change={this.changeComponent.bind(this)} update={this.updateShoppingList.bind(this)}
                             add={this.addShoppingList.bind(this)} list={this.state.shoppingLists} delete={this.deleteShoppingList.bind(this)}/>;
        this.changeComponent(this.newComp);
    }

    updateShoppingList(index, elem) {
        let shoppingLists = this.state.shoppingLists;
        elem['id'] = shoppingLists[index]['id'];
        shoppingLists[index] = elem;
        this.setState({shoppingLists: shoppingLists});
        AsyncStorage.setItem('shoppingLists',JSON.stringify(shoppingLists));
        myClonedArray  = Object.assign([], shoppingLists);
        firebase.database().ref('shoppingLists').set(myClonedArray);
    }

    deleteShoppingList(index) {

        let shoppingLists = this.state.shoppingLists;
        shoppingLists.splice(index,1)
        this.setState({shoppingLists: shoppingLists});
        AsyncStorage.setItem('shoppingLists',JSON.stringify(shoppingLists));
        myClonedArray  = Object.assign([], shoppingLists);
        firebase.database().ref('shoppingLists').set(myClonedArray);
    }

    addShoppingList(elem) {
        // elem['id'] = this.crtindex;
        let shoppingLists = this.state.shoppingLists;
        shoppingLists.push(elem);
        console.log(shoppingLists);
        this.setState({shoppingLists: shoppingLists});
        AsyncStorage.setItem('shoppingLists',JSON.stringify(shoppingLists));
        this.crtindex++;
        AsyncStorage.setItem('crtindex',JSON.stringify(this.crtindex));
        myClonedArray  = Object.assign([], shoppingLists);
        firebase.database().ref('shoppingLists').set(myClonedArray);
    }

    changeComponent(comp) {
        this.setState({component: comp});
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