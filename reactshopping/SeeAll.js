import React, {Component} from 'react';
import {Button, FlatList, ListItem, ListView, Text, TextInput, TouchableHighlight, View} from "react-native";
import Edit from "./Edit";
import Main from "./Main";
import firebase from 'react-native-firebase';

export default class SeeAll extends Component<{}> {
    constructor(props) {
        super(props);
        this.state={all:[]};
        firebase.database().ref('shoppingLists').on('value', (dataSnapshot)=>{
            console.log("NEW UPDATE SL");
            let comp = <View/>;
            let xx = this;
            xx.setState({all: dataSnapshot.val()});
        });
    }


    render() {
        this.listItems = this.state.all.map((elem, index) => {
            return {key: ("" + (index + 1) + "-" + elem.name)}
        });
        this.mainComp =
            <Main change={this.props.change} update={this.props.update} add={this.props.add} list={this.props.list} delete={this.props.delete} isAdmin={this.props.isAdmin}/>;
        let adm = this.props.isAdmin;
        return (
            <View>
                <FlatList
                    data={this.listItems}
                    renderItem={({item}) => <TouchableHighlight onPress={() => {
                        let comp
                            = <Edit change={this.props.change} update={this.props.update} add={this.props.add}
                                    list={this.props.list} elem={item.key} delete={this.props.delete} isAdmin={adm}/>;
                        this.props.change(comp);
                    }}>
                        <Text>{item.key}</Text></TouchableHighlight>}
                >
                </FlatList>

                <Button
                    onPress={() => {
                        this.props.change(this.mainComp);
                    }}
                    title="Back"
                    color="#841584"
                    accessibilityLabel="Go back"
                />
            </View>

        );
    }
}