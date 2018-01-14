import React, {Component}  from 'react';
import {View, Text, TextInput, Button} from 'react-native';
import firebase from 'react-native-firebase';
export class Login extends Component {
    constructor(props) {
        super(props);
        this.email = 'langchristian96@gmail.com';
        this.password = 'testtest';
    }

    render() {
        let loginfct=this.props.login;
        let mail = this.email;
        let pass = this.password;
        return (
            <View>
                <Text>LOGIN</Text>
                <TextInput
                    style={{width: "80%", borderWidth: 1, backgroundColor: 'white'}}
                    onChangeText={(email) => this.email = email}
                    placeholder={mail}
                />
                <TextInput
                    style={{width: "80%", borderWidth: 1, backgroundColor: 'white'}}
                    onChangeText={(password) => this.password = password}
                    secureTextEntry={true}
                    placeholder={pass}
                />
                <Button
                    title="Log in"
                    onPress={() => {
                        firebase.auth().signInWithEmailAndPassword(this.email, this.password)
                            .then(function () {
                                alert("Welcome " + firebase.auth().currentUser.email + "!");
                                loginfct(firebase.auth().currentUser.email);
                            }).catch(function (error) {
                            alert(error.code);
                            alert(error.message);
                        });
                    }}
                />


                <Button
                    title="Sign up"
                    onPress={() => {
                        firebase.auth().createUserWithEmailAndPassword(this.email, this.password)
                            .then(function () {
                                alert("Welcome " + firebase.auth().currentUser.email + "!");
                                loginfct(firebase.auth().currentUser.email);

                            }).catch(function (error) {
                            alert(error.code);
                            alert(error.message);
                        });
                    }}
                />
            </View>
        )
    }
}