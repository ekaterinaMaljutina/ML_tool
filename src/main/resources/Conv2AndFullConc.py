import argparse as args
import numpy as np

import tflearn
from sklearn.utils import shuffle
from tflearn.layers.core import input_data, dropout, fully_connected
from tflearn.layers.conv import conv_2d, max_pool_2d
from tflearn.layers.estimator import regression

tflearn.activations
from sklearn.model_selection import train_test_split


def dense_to_one_hot(labels_dense, num_classes=10):
    num_labels = labels_dense.shape[0]
    index_offset = np.arange(num_labels) * num_classes
    labels_one_hot = np.zeros((num_labels, num_classes))
    labels_one_hot.flat[index_offset + labels_dense.ravel()] = 1
    return labels_one_hot


def conv2_with_relu_and_full_connent_network(lerning_rate=0.001):
    network = input_data(shape=[None, 28, 28, 1], name='input')

    network = conv_2d(network, nb_filter=3, filter_size=3, strides=1, activation='relu')
    network = max_pool_2d(network, kernel_size=3, strides=2)

    network = conv_2d(network, nb_filter=3, filter_size=3, strides=1, activation='relu')
    network = max_pool_2d(network, kernel_size=3, strides=2)

    network = fully_connected(network, 1024 * 4, activation='relu')
    network = fully_connected(network, 1024 * 2, activation='relu')
    network = fully_connected(network, 1024 * 1, activation='relu')

    network = fully_connected(network, 10, activation='softmax')
    network = regression(network, optimizer='adam', learning_rate=lerning_rate, loss='categorical_crossentropy',
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
    # argument.add_argument('-func_act', '--func_act', dest="func_act", help='Activation function')
    argument.add_argument('-lr', '--lr', dest='lr', help='initial lerning rate')

    value = argument.parse_args()

    if value.data is None:
        argument.print_help()
        exit()

    file_with_data = value.data

    data = np.load(file=file_with_data, allow_pickle=True)
    print "data {} is load".format(file_with_data)
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
    if value.lr is None:
        fit_and_predict(conv2_with_relu_and_full_connent_network(), data_dict,
                        "_".join(["conv2_with_relu_and_full_connent_network_{}".format('0.001'), ""]))
    else:
        lr = float(value.lr)
        fit_and_predict(conv2_with_relu_and_full_connent_network(lr), data_dict,
                        "_".join(["conv2_with_relu_and_full_connent_network_{}".format(lr), ""]))


if __name__ == '__main__':
    main()
