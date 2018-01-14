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
        this.administrator = "";
        this.crtuser = "";
        firebase.database().ref('admin').on('value', (dataSnapshot)=>{
            this.administrator = dataSnapshot.val();
        });
        this.crtindex = 0;
        this.state = {shoppingLists: "", component: ""};
        this.state.shoppingLists = [];
        this.comp=<Login login={this.loginUser.bind(this)}/>;
        // this.comp = <Main change={this.changeComponent.bind(this)} update={this.updateShoppingList.bind(this)}
        //                   add={this.addShoppingList.bind(this)} list={this.state.shoppingLists} delete={this.deleteShoppingList.bind(this)}/>;
        this.state.component = this.comp;
        firebase.messaging().onMessage(function(payload) {
            alert("Message received: " + payload.fcm.body);
        })
        let xx = this;
        firebase.database().ref('crtindex').on('value', (dataSnapshot)=>{
            xx.crtindex = dataSnapshot.val();
            AsyncStorage.setItem('crtindex',dataSnapshot.val());
        });
        // AsyncStorage.getItem('crtindex').then((value, err) => {
        //     console.log(err,value);
        //     if (!err && value != null) {
        //         this.crtindex = JSON.parse(value);
        //     }
        //     else {
        //         AsyncStorage.setItem('crtindex', JSON.stringify(this.crtindex));
        //     }
        //     firebase.database().ref('crtindex').on('value', (dataSnapshot)=>{
        //         this.crtindex = dataSnapshot.val();
        //         AsyncStorage.setItem('crtindex',dataSnapshot.val());
        //     });
        //
        // });
        // AsyncStorage.setItem('shoppingLists',JSON.stringify(this.state.shoppingLists));

        firebase.database().ref('shoppingLists').on('value', (dataSnapshot)=>{
            console.log("NEW UPDATE SL");
            let comp = <View/>;
            let sls = dataSnapshot.val();
            xx.setState({shoppingLists: dataSnapshot.val()});
            AsyncStorage.setItem('shoppingLists',dataSnapshot.val());
        });
        // AsyncStorage.getItem('shoppingLists').then((value, err) => {
        //     console.log(err,value);
        //     if (!err && value != null) {
        //         console.log("Expected sls set: ",value);
        //         this.setState({shoppingLists: JSON.parse(value)});
        //         console.log("New main render list",this.state.shoppingLists);
        //     }
        //     else {
        //         console.log("sl not set");
        //         this.setState({shoppingLists: []});
        //         AsyncStorage.setItem('shoppingLists',JSON.stringify(this.state.shoppingLists));
        //         firebase.database().ref('shoppingLists').set([]);
        //     }
        // });
    }

    getShoppingList() {
        return this.state.shoppingLists;
    }

    loginUser(username) {
        this.crtuser = username;
        console.log("login function");
        this.newComp = <Main change={this.changeComponent.bind(this)} update={this.updateShoppingList.bind(this)}
                             add={this.addShoppingList.bind(this)} list={this.state.shoppingLists} delete={this.deleteShoppingList.bind(this)} isAdmin={this.isAdmin.bind(this)} login={this.loginUser.bind(this)}
                             addProduct={this.addProductToList.bind(this)}/>;
        this.changeComponent(this.newComp);
    }

    isAdmin() {
        return this.crtuser == this.administrator;
    }

    updateShoppingList(index, elem) {
        let shoppingLists = this.state.shoppingLists;
        elem['id'] = shoppingLists[index]['id'];
        shoppingLists[index] = elem;
        this.setState({shoppingLists: shoppingLists});
        AsyncStorage.setItem('shoppingLists',JSON.stringify(shoppingLists));
        myClonedArray  = Object.assign([], shoppingLists);
        firebase.database().ref('shoppingLists').child(''+index).set(elem);
    }

    addProductToList(index, product) {
        let shoppingLists = this.state.shoppingLists;
        if(shoppingLists[index].products == undefined) {
            shoppingLists[index].products = [];
        }
        shoppingLists[index].products.push(product);

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
        firebase.database().ref('crtindex').set(this.crtindex);
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