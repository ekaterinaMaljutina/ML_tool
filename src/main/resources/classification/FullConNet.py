from __future__ import print_function
import argparse as args
import numpy as np

import tflearn
from sklearn.utils import shuffle
from tflearn.layers.core import input_data, dropout, fully_connected
from tflearn.layers.conv import conv_2d, max_pool_2d
from tflearn.layers.estimator import regression

tflearn.activations
from sklearn.model_selection import train_test_split

import sys


def print_(s, end='\n', file=sys.stdout):
    file.write(s + end)
    file.flush()


def dense_to_one_hot(labels_dense, num_classes=10):
    num_labels = labels_dense.shape[0]
    index_offset = np.arange(num_labels) * num_classes
    labels_one_hot = np.zeros((num_labels, num_classes))
    labels_one_hot.flat[index_offset + labels_dense.ravel()] = 1
    return labels_one_hot


def full_connent_network(activaction_='tanh', lerning_rate=0.001):
    network = input_data(shape=[None, 28, 28, 1], name='input')
    network = fully_connected(network, n_units=1024 * 4, activation='tanh')
    network = fully_connected(network, n_units=1024 * 2, activation=activaction_)
    network = fully_connected(network, n_units=1024 * 1, activation=activaction_)
    network = fully_connected(network, 10, activation='softmax')
    network = regression(network, optimizer='sgd', learning_rate=lerning_rate, loss='categorical_crossentropy',
                         name='target')
    return network


def fit_and_predict(net, data, label):
    model = tflearn.DNN(net, tensorboard_verbose=3, tensorboard_dir='./learn_logs/')
    model.fit({'input': data["X_train"]}, {'target': data["y_train"]},
              validation_set=({'input': data["X_test"]}, {'target': data["y_test"]}),
              n_epoch=30,
              snapshot_step=20,
              show_metric=True,
              run_id=label,
              batch_size=256)


def main():
    argument = args.ArgumentParser(description="Full connected network")

    argument.add_argument('-data', '--data', dest="data", help='File with dataset')
    argument.add_argument('-func_act', '--func_act', dest="func_act", help='Activation function')
    argument.add_argument('-lr', '--lr', dest='lr', help='initial lerning rate')

    value = argument.parse_args()

    if value.data is None:
        argument.print_help()
        exit()

    file_with_data = value.data

    data = np.load(file=file_with_data, allow_pickle=True)
    print_("data {} is load".format(file_with_data))
    X, Y = data['test_dataset'], data['test_labels']
    X_val, y_val = data['valid_dataset'][:1000], data['valid_labels'][:1000]
    del data

    # Split data on train and test
    X, Y = shuffle(X, Y)
    X_train, X_test, y_train, y_test = train_test_split(X, Y, random_state=42, test_size=0.2, stratify=Y)
    del X, Y

    X_train = X_train.reshape([-1, 28, 28, 1])
    y_train = dense_to_one_hot(y_train, 10)

    X_test = X_test.reshape([-1, 28, 28, 1])
    y_test = dense_to_one_hot(y_test)

    X_val = X_val.reshape([-1, 28, 28, 1])
    y_val = dense_to_one_hot(y_val)

    data_dict = {
        "X_train": X_train,
        "y_train": y_train,
        "X_test": X_test,
        "y_test": y_test,
        "X_val": X_val,
        "y_val": y_val
    }
    if value.lr is None and value.func_act is not None:
        func_activation = value.func_act
        fit_and_predict(full_connent_network(activaction_=func_activation), data_dict,
                        "_".join(["full_connect_{}_{}".format(func_activation, 0.001), ""]))
    elif value.lr is not None and value.func_act is not None:
        lr = float(value.lr)
        func_activation = value.func_act
        fit_and_predict(full_connent_network(activaction_=func_activation, lerning_rate=lr), data_dict,
                        "_".join(["full_connect_{}_{}".format(func_activation, lr), ""]))
    elif value.lr is not None and value.func_act is None:
        lr = float(value.lr)
        fit_and_predict(full_connent_network(lerning_rate=lr), data_dict,
                        "_".join(["full_connect_tang_{}".format(lr), ""]))
    else:
        fit_and_predict(full_connent_network(), data_dict,
                        "_".join(["full_connect_tang_{}".format(0.001), ""]))


if __name__ == '__main__':
    main()
