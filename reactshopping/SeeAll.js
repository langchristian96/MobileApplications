import React, { Component } from 'react';
import {Button, FlatList, ListItem, ListView, Text, TextInput, TouchableHighlight, View} from "react-native";
import Edit from "./Edit";
import Main from "./Main";

export default class SeeAll extends Component<{}> {
    constructor(props) {
        super(props);
        this.all = this.props.list;
        this.listItems = this.all.map((elem, index) => {return {key: (""+(index+1)+"-"+elem.name)}});
        this.mainComp = <Main change={this.props.change} update={this.props.update} add={this.props.add} list={this.props.list}/>;
    }


    render() {
        return (
            <View>
                <FlatList
                    data={this.listItems}
                    renderItem={({item}) => <TouchableHighlight onPress={()=>{
                        let comp
                            =<Edit  change={this.props.change} update={this.props.update} add={this.props.add} list={this.props.list} elem={item.key}/>;
                        this.props.change(comp);}}>
                        <Text>{item.key}</Text></TouchableHighlight>}
                >
                </FlatList>

                <Button
                    onPress={() => {this.props.change(this.mainComp);}}
                    title="Back"
                    color="#841584"
                    accessibilityLabel="Go back"
                />
            </View>

        );
    }
}