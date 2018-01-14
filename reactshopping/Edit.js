import React, {Component} from 'react';
import {Button, FlatList, ListItem, ListView, Text, TextInput, TouchableHighlight, View, Alert} from "react-native";
import Main from "./Main";
import {Bar} from 'react-native-pathjs-charts';

export default class Edit extends Component<{}> {
    constructor(props) {
        super(props);
        this.itemIndex = this.props.elem.split("-")[0] - 1;
        this.list = this.props.list;
        this.state = {description: this.list[this.itemIndex].description, name: this.list[this.itemIndex].name, time: this.list[this.itemIndex].time};
        this.mainComp =
            <Main change={this.props.change} update={this.props.update} add={this.props.add} list={this.props.list} delete={this.props.delete} isAdmin={this.props.isAdmin}/>;
    }

    render() {
        isAdmin = this.props.isAdmin();
        if(isAdmin) {
            return (
                <View>
                    <TextInput
                        style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                        onChangeText={(text) => this.setState({name: text})}
                        value={this.state.name}
                    />
                    <TextInput
                        style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                        onChangeText={(text) => this.setState({description: text})}
                        value={this.state.description}
                    />

                    <Text>{this.state.time}</Text>
                    <Button
                        onPress={() => {
                            this.props
                                .update(this.itemIndex, {name: this.state.name, description: this.state.description});
                        }
                        }
                        title="Update"
                        color="#841584"
                        accessibilityLabel="Update shopping list"
                    />
                    <Button
                        onPress={() => {
                            let delfunction = this.props.delete;
                            let changefunction = this.props.change;
                            Alert.alert(
                                'Are you sure?',
                                'Are you sure you want to delete this?',
                                [
                                    {
                                        text: 'Yes', onPress: () => {
                                        // this.props
                                        delfunction(this.itemIndex);
                                        changefunction(this.mainComp);
                                    }
                                    },
                                    {text: 'Cancel', onPress: () => console.log('Cancel Pressed'), style: 'cancel'},
                                ],
                                {cancelable: false}
                            );
                        }
                        }
                        title="Delete"
                        color="#841584"
                        accessibilityLabel="Delete shopping list"
                    />

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
        else {
            return (
                <View>
                    <TextInput
                        style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                        onChangeText={(text) => this.setState({name: text})}
                        value={this.state.name}
                    />
                    <TextInput
                        style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                        onChangeText={(text) => this.setState({description: text})}
                        value={this.state.description}
                    />

                    <Text>{this.state.time}</Text>
                    <Button
                        onPress={() => {
                            this.props
                                .update(this.itemIndex, {name: this.state.name, description: this.state.description});
                        }
                        }
                        title="Update"
                        color="#841584"
                        accessibilityLabel="Update shopping list"
                    />
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
}